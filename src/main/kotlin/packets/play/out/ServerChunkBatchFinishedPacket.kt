package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

class ServerChunkBatchFinishedPacket(
    var batchSize: Int
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x0C, ResourceLocation.vanilla("play.out.chunk_batch_finished"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(batchSize)
        }
    }

}