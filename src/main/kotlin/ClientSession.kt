package com.aznos

import com.aznos.datatypes.VarInt
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.packets.Packet
import com.aznos.packets.PacketHandler
import com.aznos.packets.PacketRegistry
import com.aznos.packets.login.out.ServerLoginDisconnectPacket
import com.aznos.packets.play.out.ServerKeepAlivePacket
import com.aznos.packets.play.out.ServerPlayDisconnectPacket
import java.io.DataInputStream
import java.io.IOException
import java.net.Socket
import java.util.*
import kotlin.time.Duration.Companion.seconds

/**
 * Represents a session between a connected client and the server
 *
 * @param socket The clients socket connection
 * @property out The output stream to send packets to the client
 * @property input The input stream to read packets from the client
 * @property handler The packet handler to handle incoming packets
 */
class ClientSession(
    private val socket: Socket,
) : AutoCloseable {
    private val out = socket.getOutputStream()
    private val input = DataInputStream(socket.getInputStream())
    private val handler = PacketHandler(this)

    var state = GameState.HANDSHAKE
    var protocol = -1

    var username: String? = null
    var uuid: UUID? = null

    /**
     * This timer will keep track of when to send the keep alive packet to the client
     */
    private var keepAliveTimer: Unit? = null
    var respondedToKeepAlive: Boolean = true

    /**
     * Reads and processes incoming packets from the client
     * It reads the packet length, ID, and data, then dispatches the packet to the handler
     */
    fun handle() {
        while (!isClosed()) {
            val len = input.readVarInt()
            val id = input.readVarInt()
            val dataLength = len - VarInt.getVarIntSize(id)
            val data = ByteArray(dataLength)
            input.readFully(data)

            val packetClass = PacketRegistry.getPacket(state, id)
            if (packetClass != null) {
                val packet: Packet = packetClass
                    .getConstructor(ByteArray::class.java)
                    .newInstance(data)
                handler.handle(packet)
            } else {
                println("No registered packet for state $state with id $id")
            }
        }
    }

    fun scheduleKeepAlive() {
        keepAliveTimer = Timer(true).scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(!respondedToKeepAlive) {
                    disconnect("Timed out")
                    cancel()
                    return
                }
                sendPacket(ServerKeepAlivePacket(System.currentTimeMillis()))
                respondedToKeepAlive = true
            }
        }, 10.seconds.inWholeMilliseconds, 10.seconds.inWholeMilliseconds)
    }

    /**
     * Disconnects the client that is in the play state with the server play disconnect packet
     *
     * @param message The message to be sent to the client
     */
    fun disconnect(message: String) {
        if(state == GameState.PLAY) {
            sendPacket(ServerPlayDisconnectPacket(message))
        } else if(state == GameState.LOGIN) {
            sendPacket(ServerLoginDisconnectPacket(message))
        }

        close()
    }

    /**
     * Sends a packet to the client
     *
     * @param packet The packet to be sent
     */
    fun sendPacket(packet: Packet) {
        out.write(packet.retrieveData())
        out.flush()
    }

    /**
     * Checks if the clients socket is closed or not bound
     *
     * @return true If the socket is closed
     */
    private fun isClosed(): Boolean {
        return socket.let {
            it.isClosed || !it.isBound
        }
    }

    /**
     * Closes connection
     */
    override fun close() {
        keepAliveTimer = null
        socket.close()
    }
}