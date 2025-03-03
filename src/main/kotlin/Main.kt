package com.aznos

import com.aznos.events.EventManager
import com.aznos.events.PlayerJoinEvent
import com.aznos.events.PlayerPreJoinEvent

/**
 * Application entry point
 */
fun main() {
    Bullet.createServer("0.0.0.0")
}