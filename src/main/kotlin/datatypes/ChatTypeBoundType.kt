package com.aznos.datatypes

import com.aznos.datatypes.ComponentType.readComponent
import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.datatypes.OptionalType.readOptional
import com.aznos.datatypes.OptionalType.writeOptional
import com.aznos.entity.player.data.chat.ChatType
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object ChatTypeBoundType {

    @Throws(IOException::class)
    fun DataInputStream.readChatTypeBoundNetwork(): ChatType.Bound {
        val type = ChatType.readDirect(this)
        val name = readComponent()
        val targetName = readOptional { readComponent() }
        return ChatType.Bound(type, name, targetName)
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeChatTypeBoundNetwork(chatFormatting: ChatType.Bound) {
        chatFormatting.chatType.writeDirect(this)
        writeComponent(chatFormatting.name)
        writeOptional(chatFormatting.target) { os, v -> writeComponent(v) }
    }

}