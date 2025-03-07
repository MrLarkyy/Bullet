package com.aznos.packets.newPacket

import com.aznos.entity.player.Player

object PacketHandler {

    fun handle(player: Player, packet: ClientPacket<*>) {
        packet.handle(player)
    }

}