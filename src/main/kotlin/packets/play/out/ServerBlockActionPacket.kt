package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.Pos3i
import com.aznos.packets.data.toLong
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

class ServerBlockActionPacket(
    var pos: Pos3i,
    var actionId: Int,
    var actionData: Int,
    var blockTypeId: Int
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x08,ResourceLocation.vanilla("play.out.block_event"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeLong(pos.toLong())
            writeByte(actionId)
            writeByte(actionData)
            writeVarInt(blockTypeId)
        }
    }

}