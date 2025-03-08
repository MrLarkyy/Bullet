package com.aznos.packets.play.out

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation.Companion.vanilla
import com.aznos.packets.newPacket.ServerPacket

class ServerBundleDelimiterPacket: ServerPacket(key) {

    companion object {
        val key = Keyed(0x00, vanilla("play.out.bundle_delimiter"))
    }

    override fun retrieveData(): ByteArray {
        return byteArrayOf()
    }

}