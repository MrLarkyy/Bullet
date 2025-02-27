package com.aznos.packets.status.`in`

import com.aznos.packets.Packet

/**
 * Packet representing a ping request from the client
 * The server sends back a server status pong packet
 *
 * @property payload The long timestamp of the ping request
 */
class ClientStatusPingPacket(data: ByteArray) : Packet(data) {
    var payload: Long = 0

    init {
        payload = getIStream().readLong()
    }
}
