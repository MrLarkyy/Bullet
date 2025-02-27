package com.aznos.packets

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * Container for all kinds of packets
 *
 * @param data The packet data
 */
open class Packet(
    val data: ByteArray
) {
    val buffer = ByteArrayOutputStream()
    val wrapper = DataOutputStream(buffer)

    init {
        wrapper.write(data)
        wrapper.flush()
    }

    fun getIStream() : DataInputStream {
        return DataInputStream(ByteArrayInputStream(buffer.toByteArray()))
    }
}