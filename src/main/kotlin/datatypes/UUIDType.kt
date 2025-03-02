package com.aznos.datatypes

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.UUID

object UUIDType {
    /**
     * Reads a UUID from the [DataInputStream]
     *
     * @return Decoded UUID
     * @throws IOException If an I/O error occurs while reading the input stream
     */
    @Throws(IOException::class)
    fun DataInputStream.readUUID(): UUID {
        val mostSigBits = readLong()
        val leastSigBits = readLong()
        return UUID(mostSigBits, leastSigBits)
    }

    /**
     * Writes a UUID to the [DataOutputStream]
     *
     * @param uuid The UUID to encode
     * @throws IOException If an I/O error occurs while writing to the output stream
     */
    @Throws(IOException::class)
    fun DataOutputStream.writeUUID(uuid: UUID) {
        writeLong(uuid.mostSignificantBits)
        writeLong(uuid.leastSignificantBits)
    }
}