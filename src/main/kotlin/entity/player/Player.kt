package com.aznos.entity.player

import com.aznos.ClientSession
import com.aznos.entity.Entity
import com.aznos.entity.player.data.GameMode
import com.aznos.entity.player.data.Location
import java.util.UUID

class Player(
    val clientSession: ClientSession
) : Entity() {
    lateinit var username: String
    lateinit var uuid: UUID
    lateinit var location: Location
    lateinit var gameMode: GameMode
}