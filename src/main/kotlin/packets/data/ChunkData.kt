package com.aznos.packets.data

import dev.dewy.nbt.tags.collection.CompoundTag

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
}
