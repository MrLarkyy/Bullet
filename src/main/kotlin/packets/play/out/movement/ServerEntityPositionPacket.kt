package com.aznos.packets.play.out.movement

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import com.aznos.entity.player.data.ChatPosition
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import java.util.UUID

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
    entityID: Int,
    deltaX: Short,
    deltaY: Short,
    deltaZ: Short,
    onGround: Boolean
) : Packet(0x27) {
    init {
        wrapper.writeVarInt(entityID)
        wrapper.writeShort(deltaX.toInt())
        wrapper.writeShort(deltaY.toInt())
        wrapper.writeShort(deltaZ.toInt())
        wrapper.writeBoolean(onGround)
    }
}