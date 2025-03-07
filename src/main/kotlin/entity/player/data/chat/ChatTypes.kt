package com.aznos.entity.player.data.chat

@Suppress("unused")
object ChatTypes {

    private val map = HashMap<String, ChatType>()


    private fun define(key: String): ChatType {
        return define(key, ChatTypeDecoration.withSender("chat.type.text"))
    }

    private fun define(key: String, chatDecoration: ChatTypeDecoration): ChatType {
        return define(key, chatDecoration, ChatTypeDecoration.withSender("chat.type.text.narrate"))
    }

    private fun define(key: String, chatDecoration: ChatTypeDecoration, narrationDecoration: ChatTypeDecoration): ChatType {
        val type = ChatType(chatDecoration, null, narrationDecoration, null)
        map[key] = type
        return type
    }

    val values: Map<String,ChatType>
        get() {
            return map.toMap()
        }

    val CHAT = define("chat")
    val SAY_COMMAND = define("say_command", ChatTypeDecoration.withSender("chat.type.announcement"))
    val MSG_COMMAND_INCOMING = define("msg_command_incoming", ChatTypeDecoration.incomingDirectMessage("commands.message.display.incoming"))
    val MSG_COMMAND_OUTGOING = define("msg_command_outgoing", ChatTypeDecoration.outgoingDirectMessage("commands.message.display.outgoing"))
    val TEAM_MSG_COMMAND_INCOMING = define("team_msg_command_incoming", ChatTypeDecoration.teamMessage("chat.type.team.text"))
    val TEAM_MSG_COMMAND_OUTGOING = define("team_msg_command_outgoing", ChatTypeDecoration.teamMessage("chat.type.team.sent"))
    val EMOTE_COMMAND = define("emote_command", ChatTypeDecoration.withSender("chat.type.emote"), ChatTypeDecoration.withSender("chat.type.emote"))
    val RAW = define("raw")

}