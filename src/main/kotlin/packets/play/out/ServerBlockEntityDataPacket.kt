package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.Pos3i
import com.aznos.packets.data.toLong
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket
import dev.dewy.nbt.Nbt
import dev.dewy.nbt.tags.collection.CompoundTag

class ServerBlockEntityDataPacket(
    var pos: Pos3i,
    var entityType: Int,
    var tag: CompoundTag
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x07,ResourceLocation.vanilla("play.out.block_entity_data"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeLong(pos.toLong())
            writeVarInt(entityType)
            Nbt().toStream(tag,this)
        }
    }

}