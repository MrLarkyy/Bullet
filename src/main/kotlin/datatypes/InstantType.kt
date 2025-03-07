package com.aznos.datatypes

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.time.Instant

object InstantType {

    @Throws(IOException::class)
    fun DataInputStream.readInstant(): Instant {
        val bits = readLong()
        return Instant.ofEpochMilli(bits)
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeInstant(instant: Instant) {
        writeLong(instant.toEpochMilli())
    }

}