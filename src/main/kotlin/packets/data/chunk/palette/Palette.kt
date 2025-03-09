package com.aznos.packets.data.chunk.palette

interface Palette {

    fun size(): Int
    fun stateToId(state: Int): Int
    fun idToState(id: Int): Int

    enum class Type(val minBitsPerEntry: Int, val maxBitsPerEntry: Int, val bitShift: Int, val storageSize: Int) {
        BIOME(1, 3, 2, 64),
        CHUNK(4, 8, 4, 4096)
    }

}