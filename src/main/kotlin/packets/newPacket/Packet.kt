package com.aznos.packets.newPacket

import com.aznos.entity.player.Player
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream

abstract class Packet protected constructor(
    val resourceLocation: Keyed<ResourceLocation>
)

abstract class ServerPacket(id: Keyed<ResourceLocation>) : Packet(id) {

    fun send(player: Player) {
        player.clientSession.sendPacket(this)
    }

    protected fun writeData(block: DataOutputStream.() -> Unit): ByteArray {
        ByteArrayOutputStream().use { baos ->
            DataOutputStream(baos).use { dos ->
                block(dos)
                return baos.toByteArray()
            }
        }
    }

    abstract fun retrieveData(): ByteArray
}

abstract class ClientPacket<T : ClientPacket<T>>(data: ByteArray, resourceLocation: Keyed<ResourceLocation>) :
    Packet(resourceLocation) {

    @Suppress("UNCHECKED_CAST")
    val registry: PacketRegistry.ClientPacketEntry<T> =
        PacketRegistry.clientPackets[resourceLocation] as? PacketRegistry.ClientPacketEntry<T>
            ?: throw Exception("Packet is not registered!")

    private val buffer = ByteArrayOutputStream().apply { write(data) }
    protected fun inputStream(): DataInputStream = DataInputStream(ByteArrayInputStream(buffer.toByteArray()))

    @Suppress("UNCHECKED_CAST")
    fun handle(player: Player) {
        for ((_, handler) in registry.handlers) {
            handler.handle(player, this as T)
        }
    }

    interface Handler<T : ClientPacket<T>> {
        fun handle(sender: Player, packet: T)
    }
}