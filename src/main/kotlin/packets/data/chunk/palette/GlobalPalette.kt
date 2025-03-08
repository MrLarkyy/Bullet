package com.aznos.packets.data.chunk.palette

class GlobalPalette: Palette {
    override fun size(): Int {
        return Integer.MAX_VALUE
    }

    override fun stateToId(state: Int): Int {
        return state
    }

    override fun idToState(id: Int): Int {
        return id
    }
}