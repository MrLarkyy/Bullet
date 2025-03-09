package com.aznos.packets.configuration.out

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * This packet is sent when you want to finish the CONFIGURATION phase and switches to the PLAY phase
 */
class ServerConfigFinishPacket : ServerPacket(key) {

    companion object {
        val key = Keyed(0x03, ResourceLocation.vanilla("config.out.finish_configuration"))
    }

    override fun retrieveData(): ByteArray {
        return byteArrayOf()
    }

}