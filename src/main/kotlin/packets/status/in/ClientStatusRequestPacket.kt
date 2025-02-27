package com.aznos.packets.status.`in`

import com.aznos.packets.Packet

/**
 * Packet representing a status request from the client
 * The server sends back a server status response packet
 */
class ClientStatusRequestPacket(data: ByteArray) : Packet(data) {}
