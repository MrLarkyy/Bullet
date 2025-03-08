package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object LongArrayType {

    @Throws(IOException::class)
    fun DataInputStream.readLongArray(): LongArray {
        val size = readVarInt()
        val longArray = LongArray(size)
        for (i in 0 until size)
            longArray[i] = readLong()
        return longArray
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeLongArray(longArray: LongArray) {
        writeVarInt(longArray.size)
        for (l in longArray)
            writeLong(l)
    }
}