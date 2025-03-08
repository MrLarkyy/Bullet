package com.aznos.packets.data.chunk.palette

import com.aznos.datatypes.VarInt.readVarInt
import java.io.DataInputStream

class SingletonPalette(
    input: DataInputStream
): Palette {

    val state = input.readVarInt()

    override fun size(): Int {
        return 1
    }

    override fun stateToId(state: Int): Int {
        if (this.state == state) {
            return 0
        }
        return -1
    }

    override fun idToState(id: Int): Int {
        if (id == 0) {
            return this.state
        }
        return 0
    }
}