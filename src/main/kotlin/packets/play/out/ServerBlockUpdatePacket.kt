package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.Pos3i
import com.aznos.packets.data.toLong
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

class ServerBlockUpdatePacket(
    val pos: Pos3i,
    val blockId: Int,
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x09, ResourceLocation.vanilla("play.out.block_update"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeLong(pos.toLong())
            writeVarInt(blockId)
        }
    }

}