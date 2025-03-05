package com.aznos.entity.player

import com.aznos.entity.Entity
import com.aznos.entity.player.data.GameMode
import java.util.UUID

class Player : Entity() {
    lateinit var username: String
    lateinit var uuid: UUID
    lateinit var gameMode: GameMode
}