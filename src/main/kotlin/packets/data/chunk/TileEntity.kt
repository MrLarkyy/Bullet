package com.aznos.packets.data.chunk

import dev.dewy.nbt.tags.collection.CompoundTag

class TileEntity(
    var packedByte: Byte,
    private var y1: Short,
    var type: Int,
    var data: CompoundTag
) {

    var x: Int
        get() {
            return (packedByte.toInt() and 0xF0) shr 4
        }
        set(value) {
            this.packedByte = ((packedByte.toInt() and 0xF) or ((value and 0xF) shl 4)).toByte()
        }

    var y: Int
        get() {
            return y1.toInt()
        }
        set(value) {
            this.y1 = value.toShort()
        }

    var z: Int
        get() {
            return packedByte.toInt() and 0xF
        }
        set(value) {
            this.packedByte = ((packedByte.toInt() and 0xF0) or (value and 0xF)).toByte()
        }
}