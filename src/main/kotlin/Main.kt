package com.aznos

import com.aznos.events.EventManager
import com.aznos.events.PlayerChatEvent
import com.aznos.events.PlayerJoinEvent
import com.aznos.events.PlayerPreJoinEvent

/**
 * Application entry point
 */
fun main() {
    EventManager.register(PlayerChatEvent::class.java) { e ->
        println("Chat event fired")
    }

    Bullet.createServer("0.0.0.0")
}