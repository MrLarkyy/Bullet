package com.aznos

/**
 * Application entrypoint
 */
fun main() {
    val bullet = Bullet()
    val server = bullet.createServer("0.0.0.0")
}