package com.aznos.packets.play.out.entity

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * This packet is sent to each client whenever an entity moves to a new position
 * If an entity moves more than 8 blocks an entity teleport packet should be sent instead
 *
 * @param entityID The ID of the entity that moved
 * @param deltaX Change in X position as (currentX * 32 - prevX * 32) * 128
 * @param deltaY Change in Y position as (currentY * 32 - prevY * 32) * 128
 * @param deltaZ Change in Z position as (currentZ * 32 - prevZ * 32) * 128
 * @param onGround Whether the entity is on the ground or not
 */
class ServerEntityPositionPacket(
    var entityID: Int,
    var deltaX: Short,
    var deltaY: Short,
    var deltaZ: Short,
    var onGround: Boolean
) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x2F, ResourceLocation.vanilla("play.out.move_entity_pos"))
        const val MODERN_DELTA_DIVISOR: Double = 4096.0
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(entityID)
            writeShort((deltaX / MODERN_DELTA_DIVISOR).toInt())
            writeShort((deltaY / MODERN_DELTA_DIVISOR).toInt())
            writeShort((deltaZ / MODERN_DELTA_DIVISOR).toInt())
            writeBoolean(onGround)
        }
    }
}