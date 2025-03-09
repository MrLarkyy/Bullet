package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

class ServerChangeDifficultyPacket(
    var difficulty: Difficulty,
    var locked: Boolean
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x0B,ResourceLocation.vanilla("play.out.change_difficulty"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(difficulty.ordinal)
            writeBoolean(locked)
        }
    }

    enum class Difficulty {
        PEACEFUL,
        EASY,
        NORMAL,
        HARD;
    }

}