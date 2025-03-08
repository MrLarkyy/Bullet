package com.aznos.packets.play.`in`

import com.aznos.packets.newPacket.ClientPacket
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation

/**
 * This packet is sent in response to the server keep alive packet to tell the server we're still here
 */
class ClientKeepAlivePacket(data: ByteArray) :
    ClientPacket<ClientKeepAlivePacket>(data, key) {

    companion object {
        val key = Keyed(0x1A, ResourceLocation.vanilla("play.in.keep_alive"))
    }

}