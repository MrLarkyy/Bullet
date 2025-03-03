package com.aznos.events

import com.aznos.GameState

sealed class Events

class PlayerPreJoinEvent() : Event()
data class PlayerJoinEvent(val username: String) : Event()
data class PlayerQuitEvent(val username: String) : Event()
data class HandshakeEvent(val state: GameState, val protocol: Int) : Event()
data class StatusRequestEvent(val maxPlayers: Int, val onlinePlayers: Int, val motd: String) : Event()
data class PlayerHeartbeatEvent(val username: String) : Event()