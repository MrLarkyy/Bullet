package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * Teleports the client, e.g. during login or throwing an ender pearl or /teleport
 *
 * @param x The X position
 * @param y The Y position
 * @param z The Z position
 * @param yaw The player yaw (left-right)
 * @param pitch The player pitch (up-dpwn)
 */
class ServerSyncPlayerPosition(
    var id: Int,
    var x: Double, var y: Double,var z: Double,
    var vx: Double, var vy: Double, var vz: Double,
    var yaw: Float, var pitch: Float
) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x42, ResourceLocation.vanilla("play.out.player_position"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(id)
            writeDouble(x)
            writeDouble(y)
            writeDouble(z)
            writeDouble(vx)
            writeDouble(vy)
            writeDouble(vz)
            writeFloat(yaw)
            writeFloat(pitch)
            writeInt(0)
        }
    }
}