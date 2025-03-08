package com.aznos.packets.data

import com.aznos.entity.player.data.GameMode
import java.util.UUID

/**
 * Represents a players info entry for the player list update packet
 */
data class PlayerInfo(
    val uuid: UUID,
    val username: String,
    val properties: List<PlayerProperty> = emptyList(),
    val gameMode: GameMode = GameMode.CREATIVE,
    val ping: Int = -1,
    val hasDisplayName: Boolean = false,
    val displayName: String? = null
)