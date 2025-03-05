package com.aznos.packets.play.out

import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import java.util.UUID

/**
 * This packet is sent to all clients to inform them that a new player has spawned
 */
class ServerSpawnPlayerPacket(
    private val entityID: Int,
    private val uuid: UUID,
    private val x: Double,
    private val y: Double,
    private val z: Double,
    private val yaw: Float,
    private val pitch: Float,
) : Packet(0x04) {
    init {
        wrapper.writeVarInt(entityID)
        wrapper.writeUUID(uuid)
        wrapper.writeDouble(x)
        wrapper.writeDouble(y)
        wrapper.writeDouble(z)

        wrapper.writeByte((yaw * 256f / 360f).toInt())
        wrapper.writeByte((pitch * 256f / 360f).toInt())
    }
}