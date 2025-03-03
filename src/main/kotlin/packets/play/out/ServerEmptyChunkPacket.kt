package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import dev.dewy.nbt.Nbt
import dev.dewy.nbt.tags.collection.CompoundTag

/**
 * A chunk sent by the server to the client
 *
 * @param x The x coordinate of the chunk
 * @param z The z coordinate of the chunk
 */
class ServerEmptyChunkPacket(
    x: Int,
    z: Int
) : Packet(0x20) {
    init {
        // Chunk coordinates
        wrapper.writeInt(x)
        wrapper.writeInt(z)

        wrapper.writeBoolean(true) // Full chunk flag
        wrapper.writeVarInt(0) // Primary Bit Mask, 0 for empty chunk

        // Write Heightmaps NBT
        val tag = CompoundTag()
        tag.putLongArray("MOTION_BLOCKING", LongArray(36) { 0L })
        val nbt = Nbt()
        nbt.toStream(tag, wrapper)

        // Biomes
        wrapper.writeVarInt(1024)
        @Suppress("UnusedPrivateProperty")
        for(i in 0 until 1024) {
            wrapper.writeVarInt(0)
        }

        wrapper.writeVarInt(0) // Chunk section data
        wrapper.writeVarInt(0) // Number of block entities
    }
}