package com.aznos

import java.net.InetSocketAddress
import java.net.ServerSocket
import java.util.concurrent.Executors

/**
 * This is where the core of the bullet server logic will be housed
 */
object Bullet : AutoCloseable {
    val protocol: Int = 769; // Protocol version 769 = Minecraft version 1.21.4
    val version: String = "1.21.4"
    val maxPlayers: Int = 20;
    val description: String = "Runs as fast as a bullet"

    private val pool = Executors.newCachedThreadPool()
    private var server: ServerSocket? = null

    /**
     * Creates and runs the server instance
     *
     * @param host - The IP address of the server, for local development set this to 0.0.0.0
     * @param port - The port the server will run on, this defaults at 25565
     */
    fun createServer(host: String, port: Int = 25565) {
        server = ServerSocket().apply {
            bind(InetSocketAddress(host, port))
        }

        println("Bullet server started at $host:$port")

        while(!isClosed()) {
            val client = server?.accept()
            println("${client?.inetAddress} connected")

            pool.submit {
                client?.let {
                    ClientSession(it).handle()
                }
            }
        }
    }

    /**
     * Returns if the server instance is either closed or it is not bound
     *
     * @return True if the server is closed
     */
    private fun isClosed() : Boolean {
        return server?.let {
            it.isClosed || !it.isBound
        } ?: true
    }

    /**
     * Closes the connection to the server
     */
    override fun close() {
        server?.close()
    }
}