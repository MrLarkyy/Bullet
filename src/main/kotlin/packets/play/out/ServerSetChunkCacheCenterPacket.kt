package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * Packet sent to the client to tell the client about the center position of the clients chunk loading area
 *
 * @param x X coordinate of the loading area center
 * @param z Z coordinate of the loading area center
 */
class ServerSetChunkCacheCenterPacket(
    var x: Int = 0,
    var z: Int = 0
) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x58, ResourceLocation.vanilla("play.out.set_chunk_cache_center"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(x)
            writeVarInt(z)
        }
    }
}
