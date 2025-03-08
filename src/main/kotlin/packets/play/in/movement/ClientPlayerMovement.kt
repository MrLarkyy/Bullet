package com.aznos.packets.play.`in`.movement

import com.aznos.packets.Packet

/**
 * This packet is by the client every 20 ticks from stationary players
 */
class ClientPlayerMovement(data: ByteArray) : Packet(data) {
    val onGround: Boolean = getIStream().readBoolean()
}