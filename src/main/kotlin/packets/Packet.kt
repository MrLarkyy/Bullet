package com.aznos.packets

import com.aznos.datatypes.VarInt.writeVarInt
import java.io.*


/**
 * Base class for all packets
 *
 * @param data The raw packet data
 */
@Suppress("TooManyFunctions")
open class Packet protected constructor(
    private val data: ByteArray
) {
    private val buffer = ByteArrayOutputStream()
    val wrapper = DataOutputStream(buffer)

    /**
     * The constructor for client-bound packets
     */
    protected constructor(id: Int) : this(byteArrayOf()) {
        wrapper.writeVarInt(id)
    }

    init {
        wrapper.write(data)
        wrapper.flush()
    }

    /**
     * @return a [DataInputStream] wrapping the packet data
     */
    fun getIStream(): DataInputStream {
        return DataInputStream(ByteArrayInputStream(buffer.toByteArray()))
    }

    /**
     * Retrieves the complete packet data with its length prefixed
     *
     * @return The full packet as a byte array
     */
    fun retrieveData(): ByteArray {
        val raw = buffer.toByteArray()
        val buffer = ByteArrayOutputStream()

        buffer.writeVarInt(raw.size)
        buffer.write(raw)

        return buffer.toByteArray()
    }
}