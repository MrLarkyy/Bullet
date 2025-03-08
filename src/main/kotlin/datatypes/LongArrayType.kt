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
    fun DataInputStream.readLongArray(length: Int): LongArray {
        require(length >= 0) { "Array cannot have length less than 0." }

        val array = LongArray(length)
        val read = readLongs(array, 0, array.size)
        check(read >= length)
        return array
    }

    @Throws(IOException::class)
    fun DataInputStream.readLongs(array: LongArray, offset: Int,length: Int): Int {
        for (index in offset..<offset + length) {
            try {
                array[index] = this.readLong()
            } catch (e: Exception) {
                return index - offset
            }
        }
        return length
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeLongArray(longArray: LongArray) {
        writeVarInt(longArray.size)
        for (l in longArray)
            writeLong(l)
    }
}