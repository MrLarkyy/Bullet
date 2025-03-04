package com.aznos.packets.data

import com.aznos.datatypes.VarInt.writeVarInt
import dev.dewy.nbt.api.registry.TagTypeRegistry
import dev.dewy.nbt.tags.collection.CompoundTag
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