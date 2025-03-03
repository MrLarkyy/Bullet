package com.aznos

import com.aznos.events.EventManager
import com.aznos.events.PlayerJoinEvent

/**
 * Application entry point
 */
fun main() {
    EventManager.register(PlayerJoinEvent::class.java) { e ->
        println("Player ${e.username} joined the server!")
    }

    Bullet.createServer("0.0.0.0")
}