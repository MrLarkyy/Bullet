package com.aznos.packets.play.`in`

import com.aznos.packets.Packet

/**
 * This packet is sent in resposne to the server keep alive packet to tell the server we're still here
 */
class ClientKeepAlivePacket(data: ByteArray) : Packet(data) {
    val id: Long = getIStream().readLong()
}