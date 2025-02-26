package com.aznos

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
    private val input = socket.getInputStream()

    /**
     * This is where all data will be received
     */
    fun handle() {
        while(!isClosed()) {

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