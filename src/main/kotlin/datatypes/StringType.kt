package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets

/**
 * Utility class for handling strings with VarInt prefixed length encoding
 *
 * This format is used in communication where a string is stored as
 * a VarInt length followed by the UTF-8 encoded byte sequence
 */
object StringType {
    /**
     * Reads a string from the [DataInputStream]
     *
     * The method first reads a VarInt that represents the string length then read the
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

    /**
     * Writes a string to the [OutputStream]
     *
     * The method writes a string length as a VarInt, then write the UTF-8 encoded bytes
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