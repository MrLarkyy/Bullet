package com.aznos.packets.play.`in`.movement

import com.aznos.packets.Packet

/**
 * This packet is by the client whenever the players position and rotation is updated
 */
class ClientPlayerPositionAndRotation(data: ByteArray) : Packet(data) {
    val x: Double
    val feetY: Double
    val z: Double
    val yaw: Float
    val pitch: Float
    val onGround: Boolean

    init {
        val input = getIStream()

        x = input.readDouble()
        feetY = input.readDouble()
        z = input.readDouble()
        yaw = input.readFloat()
        pitch = input.readFloat()
        onGround = input.readBoolean()
    }
}