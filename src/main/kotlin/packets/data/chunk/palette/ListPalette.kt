package com.aznos.packets.data.chunk.palette

import com.aznos.datatypes.VarInt.readVarInt
import java.io.DataInputStream

class ListPalette(
    val bitsPerEntry: Int
): Palette {

    val maxId = (1 shl bitsPerEntry) - 1
    val data = IntArray(maxId + 1)
    var nextId = 0

    constructor(bitsPerEntry: Int,
                input: DataInputStream
    ) : this(bitsPerEntry) {
        val paletteSize = input.readVarInt()
        for (i in 0 until paletteSize) {
            data[i] = input.readVarInt()
        }
        nextId = paletteSize
    }

    override fun size(): Int {
        return nextId
    }

    override fun stateToId(state: Int): Int {
        var id = -1
        for (i in 0..<this.nextId) {
            if (data[i] == state) {
                id = i
                break
            }
        }
        if (id == -1 && this.size() < this.maxId + 1) {
            id = nextId++
            data[id] = state
        }

        return id
    }

    override fun idToState(id: Int): Int {
        return if (id >= 0 && id < this.size()) {
            data[id]
        } else {
            0
        }
    }
}