package com.aznos.packets.play.out

import com.aznos.packets.Packet

/**
 * Sends the player position and look packet to the client
 * this is the last packet needed in order for the client to join the world
 *
 * @param x The X position
 * @param y The Y position
 * @param z The Z position
 * @param yaw The player yaw (left-right)
 * @param pitch The player pitch (up-dpwn)
 */
class ServerPlayerPositionAndLookPacket(
    x: Double,
    y: Double,
    z: Double,
    yaw: Float,
    pitch: Float
) : Packet(0x34) {
    init {
        wrapper.writeDouble(x)
        wrapper.writeDouble(y)
        wrapper.writeDouble(z)
        wrapper.writeFloat(yaw)
        wrapper.writeFloat(pitch)

        wrapper.writeByte(0)
        wrapper.writeByte(0)
    }
}