package com.aznos.packets.play.out

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

class ServerChunkBatchStartPacket: ServerPacket(key) {

    companion object {
        val key = Keyed(0x0D, ResourceLocation.vanilla("play.out.chunk_batch_start"))
    }

    override fun retrieveData(): ByteArray {
        return byteArrayOf()
    }

}