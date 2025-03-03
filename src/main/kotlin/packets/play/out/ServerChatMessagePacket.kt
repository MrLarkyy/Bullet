package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.packets.Packet
import com.aznos.player.ChatMessage
import com.aznos.player.ChatPosition
import java.util.UUID

/**
 * This packet is sent to each client to display a chat message
 *
 * @param message The message to display
 * @param position Where to display the message
 * @param sender The UUID of the player who sent the message
 */
class ServerChatMessagePacket(
    message: ChatMessage,
    position: ChatPosition,
    sender: UUID?
) : Packet(0x0E) {
    init {
        wrapper.writeString(message.toJson())
        wrapper.writeByte(position.id)
        wrapper.writeUUID(sender ?: UUID(0, 0))
    }
}