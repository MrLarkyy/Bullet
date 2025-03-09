package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.Pos3i
import com.aznos.packets.data.toLong
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation.Companion.vanilla
import com.aznos.packets.newPacket.ServerPacket

class ServerSetBlockDestroyStagePacket(
    var entityId: Int,
    var position: Pos3i,
    var stage: Byte
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x06,vanilla("play.out.block_destruction"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(entityId)
            val serialized = position.toLong()
            writeLong(serialized)
            writeByte(stage.toInt())
        }
    }

}