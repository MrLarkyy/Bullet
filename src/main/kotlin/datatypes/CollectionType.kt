package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object CollectionType {

    @Throws(IOException::class)
    fun <T, K : MutableCollection<T>> DataInputStream.readCollection(
        function: (Int) -> K,
        reader: (DataInputStream) -> T
    ): K {
        val size = readVarInt()
        val collection = function(size)

        for (i in 0..<size) {
            val data = reader(this)
            collection += data
        }
        return collection
    }

    @Throws(IOException::class)
    fun <T> DataOutputStream.writeCollection(collection: Collection<T>, writer: (DataOutputStream, T) -> Unit) {
        writeVarInt(collection.size)
        for (data in collection) {
            writer(this, data)
        }
    }

}