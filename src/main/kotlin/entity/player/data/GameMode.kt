package com.aznos.entity.player.data

@Suppress("unused")
enum class GameMode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    val id: Int
        get() {
            return ordinal
        }

    companion object {
        fun byId(id: Int): GameMode? = entries.getOrNull(id)
    }
}