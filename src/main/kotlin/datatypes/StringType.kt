package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets

/**
 * Utility class for handling strings with com.aznos.datatypes.VarInt prefixed length encoding
 *
 * This format is used in communication where a string is stored as
 * a com.aznos.datatypes.VarInt length followed by the UTF-8 encoded byte sequence
 */
object StringType {
    /**
     * Reads a string from the [DataInputStream]
     *
     * The method first reads a com.aznos.datatypes.VarInt that represents the string length then read the
     * corresponding number of bytes and decodes them as a UTF-8 string
     *
     * @return Decoded string
     * @throws IOException If an I/O error occurs while reading the input stream
     */
    @Throws(IOException::class)
    fun DataInputStream.readString(): String {
        val data = ByteArray(readVarInt())
        readFully(data)

        return String(data, StandardCharsets.UTF_8)
    }

    @Throws(IOException::class)
    fun DataInputStream.readString(maxLen: Int): String {
        val len = readVarInt()

        if (len > maxLen + 4) {
            throw IOException("String length exceeds maximum allowed length of $maxLen")
        } else if (len < 0) {
            throw IOException("String length is negative")
        } else {
            val str = readString()
            if (str.length > maxLen) {
                throw IOException("String exceeds maximum allowed length of $maxLen")
            }
            return str
        }
    }

    /**
     * Writes a string to the [OutputStream]
     *
     * The method writes a string length as a com.aznos.datatypes.VarInt, then write the UTF-8 encoded bytes
     *
     * @param value The string to encode
     * @throws IOException If an I/O error occurs while reading the input stream
     */
    @Throws(IOException::class)
    fun DataOutputStream.writeString(value: String) {
        val data = value.toByteArray(StandardCharsets.UTF_8)
        writeVarInt(data.size)
        write(data)
    }
}