package com.aznos.packets.play.out

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

class ServerGameEvent(var event: Byte, var value: Float) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x23, ResourceLocation.vanilla("play.out.game_event"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeByte(event.toInt())
            writeFloat(value)
        }
    }
}