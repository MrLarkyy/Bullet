package com.aznos.packets.play.out

import com.aznos.entity.player.data.Location
import com.aznos.packets.Packet

/**
 * Sends the player position and look packet to the client
 * this is the last packet needed in order for the client to join the world
 *
 * @param x The X position
 * @param y The Y position
 * @param z The Z position
 * @param yaw The player yaw (left-right)
 * @param pitch The player pitch (up-down)
 */
class ServerPlayerPositionAndLookPacket(
    location: Location
) : Packet(0x34) {
    init {
        wrapper.writeDouble(location.x)
        wrapper.writeDouble(location.y)
        wrapper.writeDouble(location.z)
        wrapper.writeFloat(location.yaw)
        wrapper.writeFloat(location.pitch)

        wrapper.writeByte(0)
        wrapper.writeByte(0)
    }
}