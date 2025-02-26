package com.aznos

import java.net.InetSocketAddress
import java.net.ServerSocket

/**
 * This is where the core of the bullet server logic will be housed
 */
class Bullet : AutoCloseable {
    private var server: ServerSocket? = null

    /**
     * Creates and runs the server instance
     *
     * @param host - The IP address of the server, for local development set this to 0.0.0.0
     * @param port - The port the server will run on, this defaults at 25565
     */
    fun createServer(host: String, port: Int = 25565) {
        server = ServerSocket()
        server?.bind(InetSocketAddress(host, port))

        println("Bullet server started at $host:$port")

        while(!isClosed()) {
            val client = server?.accept()
            println("${client?.inetAddress} connected")
        }
    }

    /**
     * Returns if the server instance is either closed or it is not bound
     *
     * @return boolean
     * @see server
     */
    private fun isClosed() : Boolean {
        return server?.let {
            it.isClosed || !it.isBound
        } ?: true
    }

    /**
     * Closes the connection to the server
     *
     * @see server
     */
    override fun close() {
        server?.close()
    }
}