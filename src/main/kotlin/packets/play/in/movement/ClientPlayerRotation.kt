package com.aznos.packets.play.`in`.movement

import com.aznos.packets.Packet

/**
 * This packet is by the client whenever the players rotation is updated
 */
class ClientPlayerRotation(data: ByteArray) : Packet(data) {
    val yaw: Float
    val pitch: Float
    val onGround: Boolean

    init {
        val input = getIStream()

        yaw = input.readFloat()
        pitch = input.readFloat()
        onGround = input.readBoolean()
    }
}