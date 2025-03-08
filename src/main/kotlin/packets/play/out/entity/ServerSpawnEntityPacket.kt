package com.aznos.packets.play.out.entity

import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.ServerPacket
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import java.util.*
import kotlin.math.floor

/**
 * This packet is sent to all clients to inform them that a new player has spawned
 */
class ServerSpawnEntityPacket(
    var entityID: Int,
    var uuid: UUID,
    var type: Int,
    var x: Double,
    var y: Double,
    var z: Double,
    var pitch: Float,
    var yaw: Float,
    var headYaw: Float,
    var data: Int,
    var velocityX: Short,
    var velocityY: Short,
    var velocityZ: Short

) : com.aznos.packets.newPacket.ServerPacket(key) {

    companion object {
        val key = Keyed(0x01, ResourceLocation.vanilla("play.out.add_entity"))

        const val ROTATION_FACTOR: Float = 256.0f / 360.0f
        const val VELOCITY_FACTOR: Double = 8000.0
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(entityID)
            writeUUID(uuid)
            writeVarInt(type)
            writeDouble(x)
            writeDouble(y)
            writeDouble(z)

            writeByte(floor(pitch * ROTATION_FACTOR).toInt())
            writeByte(floor(yaw * ROTATION_FACTOR).toInt())

            writeByte(floor(headYaw * ROTATION_FACTOR).toInt())
            writeVarInt(data)

            val velX = (velocityX * VELOCITY_FACTOR)
            val velY = (velocityY * VELOCITY_FACTOR)
            val velZ = (velocityZ * VELOCITY_FACTOR)
            writeShort(velX.toInt())
            writeShort(velY.toInt())
            writeShort(velZ.toInt())
        }
    }
}