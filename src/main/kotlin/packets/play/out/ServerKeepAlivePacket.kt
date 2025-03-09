package com.aznos.packets.play.out

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation

/**
 * Every 20 seconds this packet will be sent to the client, it is expected to respond with a client keep alive packet
 */
class ServerKeepAlivePacket(
    var payload: Long
) : com.aznos.packets.newPacket.ServerPacket(key) {

    companion object {
        val key = Keyed(0x27, ResourceLocation.vanilla("play.in.keep_alive"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeLong(payload)
        }
    }
}