package com.aznos.packets.status.out

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * Packet for responding to the client status ping packet
 *
 * @param payload The long timestamp of the ping request
 */
class ServerStatusPongPacket(var payload: Long) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x01, ResourceLocation.vanilla("status.out.pong_response"))
    }
    override fun retrieveData(): ByteArray {
        return writeData {
            writeLong(payload)
        }
    }
}
