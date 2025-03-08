package com.aznos.packets.data.chunk.palette

import com.aznos.datatypes.VarInt.readVarInt
import java.io.DataInputStream

class MapPalette(
    val bitsPerEntry: Int,
): Palette {

    val maxId = (1 shl bitsPerEntry) - 1
    val idToState = IntArray(maxId + 1)
    val stateToId = HashMap<Any, Int>()
    var nextId = 0

    constructor(bitsPerEntry: Int, input: DataInputStream) : this(
        bitsPerEntry
    ) {

        val paletteSize = input.readVarInt()
        for (i in 0 until paletteSize) {
            val state = input.readVarInt()
            idToState[i] = state
            stateToId.putIfAbsent(state, i)
        }
        nextId = paletteSize
    }

    override fun size(): Int {
        return nextId
    }

    override fun stateToId(state: Int): Int {
        var id = stateToId[state]
        if (id == null && this.size() < this.maxId + 1) {
            id = nextId++
            idToState[id] = state
            stateToId[state] = id
        }

        return id ?: -1
    }

    override fun idToState(id: Int): Int {
        return if (id >= 0 && id < this.size()) {
            idToState[id]
        } else {
            0
        }
    }
}