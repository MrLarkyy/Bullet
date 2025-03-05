package com.aznos.packets.play.out.movement

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet

/**
 * For player entities either this packet or any move/look packet is sent every game tick
 * Meaning this packet is sent if the entity did not move/look since the last packet
 *
 * For other entities this packet may be used to initialize them
 *
 * @param entityID The ID of the entity that moved
 */
class ServerEntityMovementPacket(
    entityID: Int
) : Packet(0x2A) {
    init {
        wrapper.writeVarInt(entityID)
    }
}