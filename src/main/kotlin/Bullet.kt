package com.aznos

import com.aznos.entity.player.Player
import com.google.gson.JsonParser
import dev.dewy.nbt.api.registry.TagTypeRegistry
import dev.dewy.nbt.tags.collection.CompoundTag
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.util.concurrent.Executors

/**
 * This is where the core of the bullet server logic will be housed
 */
object Bullet : AutoCloseable {
    const val PROTOCOL: Int = 754 // Protocol version 769 = Minecraft version 1.16.5
    const val VERSION: String = "1.16.5"
    const val MAX_PLAYERS: Int = 20
    const val DESCRIPTION: String = "§6Runs as fast as a bullet"

    val logger: Logger = LogManager.getLogger()

    private val pool = Executors.newCachedThreadPool()
    private var server: ServerSocket? = null
    val players = mutableListOf<Player>()

    var dimensionCodec: CompoundTag? = null

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

        val reader = javaClass.getResourceAsStream("/codec.json")?.let {
            InputStreamReader(it)
        }

        val parsed = JsonParser.parseReader(reader).asJsonObject
        dimensionCodec = CompoundTag().fromJson(parsed, 0, TagTypeRegistry())

        logger.info("Bullet server started at $host:$port")

        while(!isClosed()) {
            val client = server?.accept()

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