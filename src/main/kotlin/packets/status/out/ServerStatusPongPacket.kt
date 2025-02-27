package com.aznos.packets.status.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.packets.Packet

/**
 * Packet for responding to the client status ping packet
 *
 * @param payload The long timestamp of the ping request
 */
class ServerStatusPongPacket(payload: Long) : Packet(0x01) {
    init {
        wrapper.writeLong(payload)
    }
}
