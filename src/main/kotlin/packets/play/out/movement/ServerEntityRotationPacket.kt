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
 * This packet is sent to each client whenever an entity moves to a new rotation axis
 *
 * @param entityID The ID of the entity that moved
 * @param yaw The new yaw of the entity
 * @param pitch The new pitch of the entity
 * @param onGround Whether the entity is on the ground or not
 */
class ServerEntityRotationPacket(
    entityID: Int,
    yaw: Float,
    pitch: Float,
    onGround: Boolean
) : Packet(0x29) {
    init {
        wrapper.writeVarInt(entityID)
        wrapper.writeByte((yaw * 256.0f / 360.0f).toInt())
        wrapper.writeByte((pitch * 256.0f / 360.0f).toInt())
        wrapper.writeBoolean(onGround)
    }
}