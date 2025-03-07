package com.aznos.datatypes

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.*

object BitSetType {


    @Throws(IOException::class)
    fun DataInputStream.readBitSet(): BitSet {
        // TODO
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeBitSet(bitSet: BitSet) {
        val longArray = bitSet.toLongArray()
        // TODO
    }
}