package com.aznos.packets.data

import com.aznos.datatypes.VarInt.writeVarInt
import dev.dewy.nbt.api.registry.TagTypeRegistry
import dev.dewy.nbt.tags.collection.CompoundTag
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

data class ChunkData(
    val heightMaps: CompoundTag,
    val data: ByteArray,
    val blockEntities: List<BlockEntity>
) {
    data class BlockEntity(
        val packedXZ: Int,
        val y: Short,
        val type: Int,
        val data: CompoundTag
    )

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as ChunkData

        if(heightMaps != other.heightMaps) return false
        if(!data.contentEquals(other.data)) return false
        if(blockEntities != other.blockEntities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = heightMaps.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + blockEntities.hashCode()
        return result
    }

    companion object {
        fun createHeightmapData(): LongArray {
            val result = LongArray(37) { 0L }
            for(i in 0 until 256) {
                val longIndex = i / 7
                val offset = (i % 7) * 9
                result[longIndex] = result[longIndex] or (2L shl offset)
            }
            return result
        }

        fun createStoneChunkSection(): ByteArray {
            val bitsPerBlock = 4
            val totalBlocks = 16 * 16 * 16

            val blockStates = IntArray(totalBlocks) { i ->
                val y = i / (16 * 16)
                if (y == 0) 1 else 0
            }

            val blockCount = 16 * 16

            val longArrayLength = (totalBlocks * bitsPerBlock + 63) / 64
            val packed = LongArray(longArrayLength) { 0L }
            for(i in 0 until totalBlocks) {
                val value = blockStates[i].toLong() and ((1L shl bitsPerBlock) - 1)
                val bitIndex = i * bitsPerBlock
                val longIndex = bitIndex / 64
                val bitOffset = bitIndex % 64

                if(bitOffset <= 64 - bitsPerBlock) {
                    packed[longIndex] = packed[longIndex] or (value shl bitOffset)
                } else {
                    val bitsInFirst = 64 - bitOffset
                    packed[longIndex] = packed[longIndex] or (value shl bitOffset)
                    packed[longIndex + 1] = packed[longIndex + 1] or (value shr bitsInFirst)
                }
            }

            val baos = ByteArrayOutputStream()
            baos.write((blockCount shr 8) and 0xFF)
            baos.write(blockCount and 0xFF)
            baos.write(bitsPerBlock)
            baos.writeVarInt(2)
            baos.writeVarInt(0)
            baos.writeVarInt(1) //The block
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

            baos.write(ByteArray(2048) { 0 })
            baos.write(ByteArray(2048) { 0xFF.toByte() })
            return baos.toByteArray()
        }
    }
}

fun writeChunkData(wrapper: DataOutputStream, chunkData: ChunkData, registry: TagTypeRegistry) {
    chunkData.heightMaps.write(wrapper, 0, registry)

    wrapper.writeVarInt(chunkData.data.size)
    wrapper.write(chunkData.data)

    wrapper.writeVarInt(chunkData.blockEntities.size)
    for(be in chunkData.blockEntities) {
        wrapper.writeVarInt(be.packedXZ)
        wrapper.writeShort(be.y.toInt())
        wrapper.writeVarInt(be.type)
        be.data.write(wrapper, 0, registry)
    }
}