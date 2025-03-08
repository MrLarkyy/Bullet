package com.aznos.packets.play.out.entity

import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation.Companion.vanilla
import com.aznos.packets.newPacket.ServerPacket

class ServerSpawnExperienceOrbPacket(
    var id: Int,
    var x: Double,
    var y: Double,
    var z: Double,
    var count: Short
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x02,vanilla("play.out.add_experience_orb"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeDouble(x)
            writeDouble(y)
            writeDouble(z)
            writeShort(count.toInt())
        }
    }
}