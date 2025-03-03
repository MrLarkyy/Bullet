package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import dev.dewy.nbt.Nbt
import dev.dewy.nbt.tags.collection.CompoundTag
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * A chunk sent by the server to the client
 * It sends one chunk section that contains a 16x1x16 grass layer
 *
 * @param x The x coordinate of the chunk
 * @param z The z coordinate of the chunk
 */
class ServerChunkPacket(
    x: Int,
    z: Int
) : Packet(0x20) {
    init {
        // Chunk coordinates
        wrapper.writeInt(x)
        wrapper.writeInt(z)

        wrapper.writeBoolean(true) // Full chunk flag
        wrapper.writeVarInt(1) // Primary Bit Mask

        // Write Heightmaps NBT
        val tag = CompoundTag()
        tag.putLongArray("MOTION_BLOCKING", createHeightmapData())
        val nbt = Nbt()
        nbt.toStream(tag, wrapper)

        // Biomes
        wrapper.writeVarInt(1024)
        @Suppress("UnusedPrivateProperty")
        for(i in 0 until 1024) {
            wrapper.writeVarInt(0)
        }

        //Chunk section data
        val sectionData = createGrassChunkSection()
        wrapper.writeVarInt(sectionData.size)
        wrapper.write(sectionData)

        wrapper.writeVarInt(0) // Number of block entities
    }

    /**
     * Creates a heightmap as a long array from 256 columns
     * where each colums height is 1
     */
    private fun createHeightmapData(): LongArray {
        val bitsPerValue = 9
        val totalColumns = 256
        val heightValues = IntArray(totalColumns) { 1 }
        val totalBits = totalColumns * bitsPerValue
        val longArrayLength = (totalBits + 63) / 64
        val packed = LongArray(longArrayLength) { 0L }

        for(i in 0 until totalColumns) {
            val value = heightValues[i].toLong() and ((1L shl bitsPerValue) - 1)
            val bitIndex = i * bitsPerValue
            val longIndex = bitIndex / 64
            val bitOffset = bitIndex % 64

            if(bitOffset <= 64 - bitsPerValue) {
                packed[longIndex] = packed[longIndex] or (value shl bitOffset)
            } else {
                val bitsInFirst = 64 - bitOffset
                packed[longIndex] = packed[longIndex] or (value shl bitOffset)
                packed[longIndex + 1] = packed[longIndex + 1] or (value shr bitsInFirst)
            }
        }

        return packed
    }

    /**
     * Create a chunk section (16x16x16) where the bottom layer is grass
     * and all other blocks are air
     */
    private fun createGrassChunkSection(): ByteArray {
        val bitsPerBlock = 4
        val totalBlocks = 16 * 16 * 16

        val blockStates = IntArray(totalBlocks) { i ->
            val y = i shr 8
            if(y == 0) 2 else 0
        }

        val totalBits = totalBlocks * bitsPerBlock
        val longArrayLength = (totalBits + 63) / 64
        val packed = LongArray(longArrayLength) { 0L }
        for(i in 0 until totalBlocks) {
            val value = blockStates[i]
            val bitIndex = i * bitsPerBlock
            val longIndex = bitIndex / 64
            val bitOffset = bitIndex % 64

            if(bitOffset <= 64 - bitsPerBlock) {
                packed[longIndex] = packed[longIndex] or (value.toLong() shl bitOffset)
            } else {
                val bitsInFirst = 64 - bitOffset
                packed[longIndex] = packed[longIndex] or (value.toLong() shl bitOffset)
                packed[longIndex + 1] = packed[longIndex + 1] or (value.toLong() shr bitsInFirst)
            }
        }

        val baos = ByteArrayOutputStream()
        baos.write(bitsPerBlock)
        baos.writeVarInt(2)
        baos.writeVarInt(0)
        baos.writeVarInt(1)
        baos.writeVarInt(packed.size)

        for(l in packed) {
            baos.write((l shr 56 and 0xFF).toInt())
            baos.write((l shr 48 and 0xFF).toInt())
            baos.write((l shr 40 and 0xFF).toInt())
            baos.write((l shr 32 and 0xFF).toInt())
            baos.write((l shr 24 and 0xFF).toInt())
            baos.write((l shr 16 and 0xFF).toInt())
            baos.write((l shr 8 and 0xFF).toInt())
            baos.write((l and 0xFF).toInt())
        }

        return baos.toByteArray()
    }
}