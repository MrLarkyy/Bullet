package com.aznos.packets.play.out.chat

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
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation

/**
 * This packet is sent to each client to display a chat message
 *
 * @param message The message to display
 * @param position Where to display the message
 * @param sender The UUID of the player who sent the message
 */
class ServerChatMessagePacket(
    val message: ChatMessage
) : com.aznos.packets.newPacket.ServerPacket(key) {

    companion object {
        val key = Keyed(0x3B, ResourceLocation.vanilla("play.out.player_chat"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeUUID(message.senderUUID)
            writeVarInt(message.index)
            writeOptional(message.signature) { _,v ->
                write(v.toByteArray())
            }
            writeString(message.plainContent)
            writeInstant(message.timestamp)
            writeLong(message.salt)
            writeLastSeenMessagesPacked(message.lastSeenMessagesPacked)
            // TODO: Kyori Component Serialization - NBT (since 1.21.4+)
            writeOptional(message.unsignedChatContent) { os, v -> os.writeComponent(v)}
            writeFilterMask(message.filterMask)
            writeChatTypeBoundNetwork(message.chatFormatting)
        }
    }
}