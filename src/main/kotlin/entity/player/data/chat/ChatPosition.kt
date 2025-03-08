package com.aznos.entity.player.data.chat

@Suppress("unused")
enum class ChatPosition {
    CHAT,
    SYSTEM,
    HOTBAR;

    val id: Int
        get() {
            return ordinal
        }

    companion object {
        fun byId(id: Int): ChatPosition? = entries.getOrNull(id)
    }
}