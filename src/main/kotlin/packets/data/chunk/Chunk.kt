package com.aznos.packets.data.chunk

import com.aznos.packets.data.chunk.palette.DataPalette
import com.aznos.packets.data.chunk.palette.Palette
import java.io.DataInputStream
import java.io.DataOutputStream

class Chunk(
    var blockCount: Int,
    val chunkData: DataPalette,
    val biomeData: DataPalette
) {

    constructor(): this(0, DataPalette.createForChunk(), DataPalette.createForBiome())

    companion object {
        fun read(input: DataInputStream): Chunk {
            val blockCount = input.readShort().toInt()
            val chunkPalette = DataPalette.read(input, Palette.Type.CHUNK, true)
            val biomePalette = DataPalette.read(input,  Palette.Type.BIOME, true)
            return Chunk(blockCount, chunkPalette, biomePalette)
        }
        fun write(output: DataOutputStream, chunk: Chunk) {
            output.writeShort(chunk.blockCount)
            DataPalette.write(output, chunk.chunkData)
            DataPalette.write(output, chunk.biomeData)
        }
    }

    fun blockId(x: Int, y: Int, z: Int): Int {
        return chunkData.get(x, y, z)
    }
    fun set(x: Int, y: Int, z: Int, state: Int) {
        val curr = chunkData.set(x, y, z, state)
        if (state != 0 && curr == 0)
            blockCount++
        else if (state == 0 && curr != 0)
            blockCount--
    }

}