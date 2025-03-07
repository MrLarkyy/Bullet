package com.aznos.packets.play.out

import com.aznos.packets.Packet
import com.aznos.packets.ServerPacket

/**
 * Every 20 seconds this packet will be sent to the client, it is expected to respond with a client keep alive packet
 */
class ServerKeepAlivePacket(
    payload: Long
) : ServerPacket(0x1F) {
    init {
        wrapper.writeLong(payload)
    }
}