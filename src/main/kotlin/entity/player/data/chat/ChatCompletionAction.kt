package com.aznos.entity.player.data.chat

enum class ChatCompletionAction {

    ADD,
    REMOVE,
    SET;

    companion object {
        fun byId(id: Int): ChatCompletionAction? = entries.getOrNull(id)
    }
}