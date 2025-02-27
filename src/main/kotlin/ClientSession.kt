package com.aznos

import com.aznos.datatypes.VarInt
import com.aznos.datatypes.VarInt.readVarInt
import java.io.DataInputStream
import java.net.Socket

/**
 * Each client will be attached its own client session between the client and the server
 * It contains all methods required to send and receive data to/from the server
 */
class ClientSession(
    private val socket: Socket,
    private val bullet: Bullet,
) : AutoCloseable {
    private val out = socket.getOutputStream()
    private val input = DataInputStream(socket.getInputStream())

    /**
     * This is where all data will be received
     */
    fun handle() {
        while(!isClosed()) {
            val len = input.readVarInt()
            val id = input.readByte()
            val data = (len - 1).toByte()
            input.readFully(byteArrayOf(data))

            println(id)
        }
    }

    private fun isClosed(): Boolean {
        return socket.let {
            it.isClosed || !it.isBound
        }
    }

    override fun close() {
        socket.close()
    }
}