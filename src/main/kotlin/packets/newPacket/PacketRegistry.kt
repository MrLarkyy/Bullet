package com.aznos.packets.newPacket

import java.util.concurrent.ConcurrentHashMap

object PacketRegistry {

    val serverPackets = KeyedRegistry<ResourceLocation, Class<ServerPacket>>(
        ConcurrentHashMap()
    )
    val clientPackets =
        KeyedRegistry<ResourceLocation, ClientPacketEntry<*>>()

    fun clientPacketHandlers(key: ResourceLocation): Map<ResourceLocation, ClientPacket.Handler<*>> =
        clientPackets[key]?.handlers ?: emptyMap()

    fun clientPacketHandlers(key: Keyed<ResourceLocation>): Map<ResourceLocation, ClientPacket.Handler<*>> =
        clientPackets[key]?.handlers ?: emptyMap()

    fun clientPacketHandlers(id: Int): Map<ResourceLocation, ClientPacket.Handler<*>> =
        clientPackets[id]?.handlers ?: emptyMap()

    fun clientPacketHandler(packetKey: ResourceLocation, handlerKey: ResourceLocation): ClientPacket.Handler<*>? =
        clientPackets[packetKey]?.handlers?.get(handlerKey)

    fun clientPacketHandler(
        packetKey: Keyed<ResourceLocation>,
        handlerKey: ResourceLocation
    ): ClientPacket.Handler<*>? =
        clientPackets[packetKey]?.handlers?.get(handlerKey)

    fun clientPacketHandler(id: Int, handlerKey: ResourceLocation): ClientPacket.Handler<*>? =
        clientPackets[id]?.handlers?.get(handlerKey)

    fun KeyedRegistry<ResourceLocation, Pair<Class<out ClientPacket>, MutableMap<ResourceLocation, ClientPacket.Handler<*>>>>.registerHandler(
        packetKey: ResourceLocation,
        handlerKey: ResourceLocation,
        handler: ClientPacket.Handler<*>
    ) {
        val handlers = this[packetKey] ?: return
        handlers.second[handlerKey] = handler
    }

    class ClientPacketEntry<T : ClientPacket>(
        val clazz: Class<T>,
        val handlers: MutableMap<ResourceLocation, ClientPacket.Handler<T>>
    )
}