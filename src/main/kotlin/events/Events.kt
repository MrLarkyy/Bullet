package com.aznos.events

import com.aznos.GameState

//Unmodifiable events

/**
 * Called right when the server receives that the client is ready to play and
 * before any checks are made to see if the client is allowed to join
 */
class PlayerPreJoinEvent : Event()

/**
 * Called when a player joins the server
 *
 * @param username The username of the player that joined
 */
data class PlayerJoinEvent(val username: String) : Event()

/**
 * Called when a player leaves the server
 *
 * @param username The username of the player that left
 */
data class PlayerQuitEvent(val username: String) : Event()

/**
 * Called whenever there is a handshake between the client and server
 *
 * @param state The current game state, handshakes are either in the status or login state
 * @param protocol The protocol version of the client
 */
data class HandshakeEvent(val state: GameState, val protocol: Int) : Event()

/**
 * Called once a heartbeat has been completed, cancelling this event will result in the client being kicked
 *
 * @param username The username of the player
 */
data class PlayerHeartbeatEvent(val username: String) : Event()

/**
 * Called when a player sends a chat message
 *
 * @param username The username of the player that sent the message
 * @param message The message that was sent
 */
data class PlayerChatEvent(val username: String, val message: String) : Event()

//Modifiable events

/**
 * Called when the server receives a status request packet to display server information
 *
 * @param maxPlayers The maximum amount of player allowed to join the server
 * @param onlinePlayers The amount of players currently online
 * @param motd The description of the server
 */
data class StatusRequestEvent(var maxPlayers: Int, var onlinePlayers: Int, var motd: String) : Event()