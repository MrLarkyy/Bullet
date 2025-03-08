package com.aznos.packets.play.`in`

import com.aznos.datatypes.InstantType.readInstant
import com.aznos.datatypes.LastSeenMessagesUpdate.readLastSeenMessagesUpdate
import com.aznos.datatypes.SaltSignatureType.readSaltSignature
import com.aznos.datatypes.StringType.readString
import com.aznos.entity.player.data.chat.LastSeenMessages
import com.aznos.packets.Packet
import com.aznos.packets.data.crypto.MessageSignData
import java.time.Instant

/**
 * This packet is sent to the server whenever the client sends a chat message
 *
 * @property message The message the client sent
 */
class ClientChatMessagePacket(data: ByteArray) : Packet(data) {

    companion object {
        const val maxMessageLength = 256
    }

    var message: String
    var messageSignData: MessageSignData
    var lastSeenMessages: LastSeenMessages.Update

    init {
        val input = getIStream()
        message = input.readString(maxMessageLength)
        val timestamp = input.readInstant()
        messageSignData = MessageSignData(input.readSaltSignature(), timestamp)
        lastSeenMessages = input.readLastSeenMessagesUpdate()
    }




}