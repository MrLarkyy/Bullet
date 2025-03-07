package com.aznos.entity.player.data.chat

import net.kyori.adventure.text.Component
import java.io.DataInputStream
import java.io.DataOutputStream

data class ChatType(
    var chatDecoration:ChatTypeDecoration,
    var overlayDecoration: ChatTypeDecoration?,
    var narrationDecoration: ChatTypeDecoration,
    var narrationPriority: NarrationPriority?
) {

    data class Bound(
        var chatType: ChatType,
        var name: Component,
        var target: Component?
    )

    fun writeDirect(os: DataOutputStream) {
        chatDecoration.write(os)
        narrationDecoration.write(os)
    }

    companion object {
        fun readDirect(input: DataInputStream): ChatType {
            val chatDeco = ChatTypeDecoration.read(input)
            val narrDeco = ChatTypeDecoration.read(input)
            return ChatType(chatDeco, null, narrDeco, null)
        }
    }


    enum class NarrationPriority(val id: String) {
        CHAT("chat"),
        SYSTEM("system");

        companion object {
            val ID_INDEX = entries.associateBy(NarrationPriority::id)
        }
    }


}