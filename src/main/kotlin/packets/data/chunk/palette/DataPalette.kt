package com.aznos.packets.data.chunk.palette

import com.aznos.datatypes.LongArrayType.readLongArray
import com.aznos.datatypes.LongArrayType.writeLongArray
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.chunk.storage.BaseStorage
import com.aznos.packets.data.chunk.storage.BitStorage
import java.io.DataInputStream
import java.io.DataOutputStream

class DataPalette(
    var palette: Palette,
    var storage: BaseStorage?,
    val paletteType: Palette.Type
) {

    companion object {
        const val GLOBAL_PALETTE_BITS_PER_ENTRY: Int = 15

        fun createForChunk(): DataPalette {
            return createEmpty(Palette.Type.CHUNK)
        }
        fun createForBiome(): DataPalette {
            return createEmpty(Palette.Type.BIOME)
        }

        fun createEmpty(paletteType: Palette.Type): DataPalette {
            return DataPalette(
                ListPalette(paletteType.minBitsPerEntry),
                BitStorage(paletteType.minBitsPerEntry, paletteType.storageSize),
                paletteType
            )
        }

        fun read(input: DataInputStream, paletteType: Palette.Type, allowSingletonPalette: Boolean): DataPalette {
            val bitsPerEntry = input.readByte().toInt()
            val palette = readPalette(paletteType, bitsPerEntry, input, allowSingletonPalette)
            val storage = if (palette !is SingletonPalette) {
                val length = input.readVarInt()
                BitStorage(bitsPerEntry, paletteType.storageSize, input.readLongArray(length))
            } else {
                input.readVarInt()
                null
            }
            return DataPalette(palette, storage, paletteType)
        }

        fun write(out: DataOutputStream, palette: DataPalette) {
            if (palette.palette is SingletonPalette) {
                out.writeByte(0)
                out.writeVarInt(palette.palette.idToState(0))
                out.writeVarInt(0)
                return
            }

            out.writeByte(palette.storage?.getBitsPerEntry() ?: 0)

            if (palette.palette !is GlobalPalette) {
                val paletteLength = palette.palette.size()
                out.writeVarInt(paletteLength)
                for (i in 0..<paletteLength) {
                    out.writeVarInt(palette.palette.idToState(i))
                }
            }

            val data = palette.storage?.getData() ?: LongArray(0)
            //out.writeVarInt(data.size)
            out.writeLongArray(data)
        }

        private fun readPalette(
            paletteType: Palette.Type,
            bitsPerEntry: Int,
            input: DataInputStream,
            allowSingletonPalette: Boolean
        ): Palette {
            if (bitsPerEntry > paletteType.maxBitsPerEntry) {
                return GlobalPalette()
            }
            if (bitsPerEntry == 0 && allowSingletonPalette) {
                return SingletonPalette(input)
            }
            return if (bitsPerEntry <= paletteType.minBitsPerEntry) {
                ListPalette(bitsPerEntry, input)
            } else {
                MapPalette(bitsPerEntry, input)
            }
        }
    }

    fun get(x: Int, y: Int, z: Int): Int {
        if (storage != null) {
            val id = storage!!.get(index(this.paletteType, x, y, z))
            return palette.idToState(id)
        } else {
            return palette.idToState(0)
        }
    }

    fun set(x: Int, y: Int, z: Int, state: Int): Int {
        var id = palette.stateToId(state)
        if (id == -1) {
            resize()
            id = palette.stateToId(state)
        }

        if (this.storage != null) {
            val index: Int = index(this.paletteType, x, y, z)
            val curr = storage!!.get(index)

            storage!!.set(index, id)
            return curr
        } else {
            return state
        }
    }


    private fun index(paletteType: Palette.Type, x: Int, y: Int, z: Int): Int {
        return (y shl paletteType.bitShift or z) shl paletteType.bitShift or x
    }

    private fun sanitizeBitsPerEntry(bitsPerEntry: Int): Int {
        return if (bitsPerEntry <= paletteType.maxBitsPerEntry) {
            paletteType.minBitsPerEntry.coerceAtLeast(bitsPerEntry)
        } else {
            GLOBAL_PALETTE_BITS_PER_ENTRY
        }
    }

    private fun resize() {
        val oldPalette = this.palette
        val oldData = this.storage

        val bitsPerEntry: Int =
            sanitizeBitsPerEntry(if (oldPalette is SingletonPalette) 1 else oldData!!.getBitsPerEntry() + 1)
        this.palette = createPalette(bitsPerEntry, paletteType)
        val storage = BitStorage(bitsPerEntry, paletteType.storageSize)
        this.storage = storage

        if (oldPalette is SingletonPalette) {
            palette.stateToId(oldPalette.idToState(0))
        } else {
            for (i in 0..<paletteType.storageSize) {
                storage.set(i, palette.stateToId(oldPalette.idToState(oldData!!.get(i))))
            }
        }
    }

    private fun createPalette(bitsPerEntry: Int, paletteType: Palette.Type): Palette {
        return if (bitsPerEntry <= paletteType.minBitsPerEntry) {
            ListPalette(bitsPerEntry)
        } else if (bitsPerEntry <= paletteType.maxBitsPerEntry) {
            MapPalette(bitsPerEntry)
        } else {
            GlobalPalette()
        }
    }

}