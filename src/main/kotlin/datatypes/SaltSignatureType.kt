package com.aznos.datatypes

import com.aznos.packets.data.crypto.SaltSignature
import java.io.DataInputStream
import java.io.IOException

object SaltSignatureType {
    @Throws(IOException::class)

    fun DataInputStream.readSaltSignature(): SaltSignature {
        val salt = readLong()
        val signature: ByteArray =
            if (readBoolean()) {
                readNBytes(256)
            } else {
                ByteArray(0)
            }
        return SaltSignature(salt, signature.toList())
    }

}