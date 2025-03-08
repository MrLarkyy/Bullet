package com.aznos.packets.play.out

import com.aznos.datatypes.ChatTypeBoundType.writeChatTypeBoundNetwork
import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.datatypes.FilterMaskType.writeFilterMask
import com.aznos.datatypes.InstantType.writeInstant
import com.aznos.datatypes.LastSeenMessagesPacked.writeLastSeenMessagesPacked
import com.aznos.datatypes.OptionalType.writeOptional
import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.entity.player.data.chat.ChatMessage
import com.aznos.packets.ServerPacket

/**
 * This packet is sent to each client to display a chat message
 *
 * @param message The message to display
 * @param position Where to display the message
 * @param sender The UUID of the player who sent the message
 */
class ServerChatMessagePacket(
    message: ChatMessage
) : ServerPacket(0x3B) {
    init {
        wrapper.writeUUID(message.senderUUID)
        wrapper.writeVarInt(message.index)
        wrapper.writeOptional(message.signature) { _,v ->
            wrapper.write(v.toByteArray())
        }
        wrapper.writeString(message.plainContent)
        wrapper.writeInstant(message.timestamp)
        wrapper.writeLong(message.salt)
        wrapper.writeLastSeenMessagesPacked(message.lastSeenMessagesPacked)
        // TODO: Kyori Component Serialization - NBT (since 1.21.4+)
        wrapper.writeOptional(message.unsignedChatContent) { os, v -> os.writeComponent(v)}
        wrapper.writeFilterMask(message.filterMask)
        wrapper.writeChatTypeBoundNetwork(message.chatFormatting)
    }
}