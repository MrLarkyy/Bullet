package com.aznos.packets.play.out.entity

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation.Companion.vanilla
import com.aznos.packets.newPacket.ServerPacket

class ServerEntityAnimationPacket(
    var id: Int,
    var type: Type
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x03,vanilla("play.out.animate"))
    }
    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(id)
            writeByte(type.ordinal)
        }
    }

    enum class Type {
        SWING_MAIN_ARM,
        HURT,
        WAKE_UP,
        SWING_OFF_HAND,
        CRITICAL_HIT,
        MAGIC_CRITICAL_HIT;

        companion object {
            fun byId(id: Int): Type = entries[id]
        }
    }

}