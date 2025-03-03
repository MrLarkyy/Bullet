package com.aznos.packets.play.`in`

import com.aznos.datatypes.StringType.readString
import com.aznos.packets.Packet

/**
 * This packet is sent to the server whenever the client sends a chat message
 *
 * @property message The message the client sent
 */
class ClientChatMessagePacket(data: ByteArray) : Packet(data) {
    val message: String = getIStream().readString()
}