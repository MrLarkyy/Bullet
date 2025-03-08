package com.aznos.packets.play.out.entity

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation

/**
 * This packet is sent to each client whenever an entity moves to a new rotation axis
 *
 * @param entityID The ID of the entity that moved
 * @param yaw The new yaw of the entity
 * @param pitch The new pitch of the entity
 * @param onGround Whether the entity is on the ground or not
 */
class ServerEntityRotationPacket(
    var entityID: Int,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean
) : com.aznos.packets.newPacket.ServerPacket(key) {

    companion object {
        val key = Keyed(0x32, ResourceLocation.vanilla("play.out.move_entity_rot"))
        const val ROTATION_FACTOR: Float = 256.0f / 360.0f
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(entityID)
            writeByte((yaw * ROTATION_FACTOR).toInt())
            writeByte((pitch * ROTATION_FACTOR).toInt())
            writeBoolean(onGround)
        }
    }
}