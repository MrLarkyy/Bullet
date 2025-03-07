package com.aznos.datatypes

import com.aznos.datatypes.LongArrayType.readLongArray
import com.aznos.datatypes.LongArrayType.writeLongArray
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.*

object BitSetType {

    @Throws(IOException::class)
    fun DataInputStream.readBitSet(): BitSet {
        return BitSet.valueOf(readLongArray())
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeBitSet(bitSet: BitSet) {
        writeLongArray(bitSet.toLongArray())
    }
}