package com.aznos.player

enum class GameMode(var id: Int) {
    NONE(-1),
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    fun getGamemodeFromID(id: Int): GameMode {
        return when(id) {
            0 -> SURVIVAL
            1 -> CREATIVE
            2 -> ADVENTURE
            3 -> SPECTATOR
            else -> NONE
        }
    }
}