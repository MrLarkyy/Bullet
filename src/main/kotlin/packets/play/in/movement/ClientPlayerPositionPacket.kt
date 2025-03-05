package com.aznos.packets.play.`in`.movement

import com.aznos.packets.Packet

/**
 * This packet is by the client whenever the players position is updated
 */
class ClientPlayerPositionPacket(data: ByteArray) : Packet(data) {
    val x: Double
    val feetY: Double
    val z: Double
    val onGround: Boolean

    init {
        val input = getIStream()

        x = input.readDouble()
        feetY = input.readDouble()
        z = input.readDouble()
        onGround = input.readBoolean()
    }
}