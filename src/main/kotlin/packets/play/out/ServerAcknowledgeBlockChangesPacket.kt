package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation.Companion.vanilla
import com.aznos.packets.newPacket.ServerPacket

class ServerAcknowledgeBlockChangesPacket(
    val sequence: Int
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x05, vanilla("play.out.block_changed_ack"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(sequence)
        }
    }

}