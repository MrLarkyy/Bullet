package com.aznos.datatypes

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object OptionalType {

    @Throws(IOException::class)
    fun <T> DataInputStream.readOptional(reader: (DataInputStream) -> T): T? {
        if (readBoolean()) {
            return reader(this)
        }
        return null
    }

    @Throws(IOException::class)
    fun <T> DataOutputStream.writeOptional(value: T?, writer: (DataOutputStream, T) -> Unit) {
        if (value != null) {
            writeBoolean(true)
            writer(this, value)
        } else writeBoolean(false)
    }
}