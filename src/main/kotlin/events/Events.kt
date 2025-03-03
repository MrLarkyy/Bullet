package com.aznos.events

import com.aznos.GameState

sealed class Events

//Unmodifiable events
class PlayerPreJoinEvent() : Event()
data class PlayerJoinEvent(val username: String) : Event()
data class PlayerQuitEvent(val username: String) : Event()
data class HandshakeEvent(val state: GameState, val protocol: Int) : Event()
data class PlayerHeartbeatEvent(val username: String) : Event()

//Modifiable events
data class StatusRequestEvent(var maxPlayers: Int, var onlinePlayers: Int, var motd: String) : Event()