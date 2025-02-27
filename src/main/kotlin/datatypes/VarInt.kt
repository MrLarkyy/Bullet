package com.aznos.datatypes

import java.io.DataInputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.jvm.Throws

/**
 * Utility class that provides methods for encoding and decoding VarInts
 *
 * VarInts are variable length integers that use byte wise encoded system where
 * the most signification bit of each byte indicates whether more bytes follow
 *
 * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/VarInt_And_VarLong">Wiki on VarInts</a>
 */
object VarInt {
    /**
     * Reads a VarInt from the [DataInputStream]
     *
     * @return The decoded integer
     * @throws IOException If an I/O error occurs while reading the input stream
     * @throws RuntimeException If the VarInt exceeds 5 bytes (out of range)
     */
    @Throws(IOException::class)
    fun DataInputStream.readVarInt(): Int {
        var numRead = 0
        var result = 0
        var read: Byte

        do {
            read = readByte()

            val value = read.toInt() and 0b01111111
            result = result or (value shl (7 * numRead))

            numRead++
            if(numRead > 5) throw RuntimeException("VarInt too big")
        } while((read.toInt() and 0b10000000) != 0)

        return result
    }

    /**
     * Writes a VarInt to the [OutputStream]
     *
     * @param value The integer to be encoded
     * @throws IOException If an I/O error occurs while reading the input stream
     */
    @Throws(IOException::class)
    fun OutputStream.writeVarInt(value: Int) {
        var tempVal = value
        do {
            var temp = (tempVal and 0b01111111).toByte()
            tempVal = tempVal ushr 7

            if(tempVal != 0) {
                temp = (temp.toInt() or 0b10000000).toByte()
            }

            write(temp.toInt())
        } while(tempVal != 0)
    }
}