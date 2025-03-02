package com.aznos.packets

import com.aznos.Bullet
import com.aznos.ClientSession
import com.aznos.GameState
import com.aznos.datatypes.UUIDType
import com.aznos.packets.data.ServerStatusResponse
import com.aznos.packets.login.`in`.ClientLoginStartPacket
import com.aznos.packets.login.out.ServerLoginDisconnectPacket
import com.aznos.packets.login.out.ServerLoginSuccessPacket
import com.aznos.packets.play.out.ServerJoinGamePacket
import com.aznos.packets.play.out.ServerPlayerPositionAndLookPacket
import com.aznos.packets.status.`in`.ClientStatusPingPacket
import com.aznos.packets.status.`in`.ClientStatusRequestPacket
import com.aznos.packets.status.out.ServerStatusPongPacket
import com.aznos.player.GameMode
import kotlinx.serialization.json.Json
import packets.handshake.HandshakePacket
import packets.status.out.ServerStatusResponsePacket
import java.util.UUID

/**
 * Handles all incoming packets by dispatching them to the appropriate handler methods
 *
 * @property client The clients session
 */
class PacketHandler(
    private val client: ClientSession
) {
    /**
     * Handles when the client tells the server it's ready to log in
     *
     * The server first checks for a valid version and uuid, then
     */
    @PacketReceiver
    fun onLoginStart(packet: ClientLoginStartPacket) {
        if(client.protocol > Bullet.PROTOCOL) {
            client.sendPacket(ServerLoginDisconnectPacket("Please downgrade your minecraft version to " + Bullet.VERSION))
            return
        } else if(client.protocol < Bullet.PROTOCOL) {
            client.sendPacket(ServerLoginDisconnectPacket("Your client is outdated, please upgrade to minecraft version " + Bullet.VERSION))
            return
        }

        val username = packet.username
        if(!username.matches(Regex("^[a-zA-Z0-9]{3,16}$"))) { // Alphanumeric and 3-16 characters
            client.sendPacket(ServerLoginDisconnectPacket("Invalid username"))
            return
        }

        val uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:$username").toByteArray())
        client.username = username
        client.uuid = uuid

        client.sendPacket(ServerLoginSuccessPacket(uuid, username))
        client.state = GameState.PLAY

        client.sendPacket(ServerJoinGamePacket(
            0,
            false,
            GameMode.CREATIVE,
            "minecraft:overworld",
            Bullet.dimensionCodec!!,
            Bullet.MAX_PLAYERS,
            8,
            false,
            true,
            false,
            true
        ))

        client.sendPacket(ServerPlayerPositionAndLookPacket(0.0, 0.0, 0.0, 0f, 0f))
    }

    /**
     * Handles a ping packet by sending a pong response and closing the connection
     */
    @PacketReceiver
    fun onPing(packet: ClientStatusPingPacket) {
        client.sendPacket(ServerStatusPongPacket(packet.payload))
        client.close()
    }

    /**
     * Handles a status request packet by sending a server status response
     */
    @PacketReceiver
    fun onStatusRequest(packet: ClientStatusRequestPacket) {
        val response = ServerStatusResponse(
            ServerStatusResponse.Version(Bullet.VERSION, Bullet.PROTOCOL),
            ServerStatusResponse.Players(Bullet.MAX_PLAYERS, 0),
            Bullet.DESCRIPTION,
            false
        )

        client.sendPacket(ServerStatusResponsePacket(Json.encodeToString(response)))
    }

    /**
     * Handles a handshake packet by updating the client state and protocol
     */
    @PacketReceiver
    fun onHandshake(packet: HandshakePacket) {
        client.state = if(packet.state == 2) GameState.LOGIN else GameState.STATUS
        client.protocol = packet.protocol ?: -1
    }

    /**
     * Dispatches the given packet to the corresponding handler method based on its type
     *
     * @param packet The packet to handle
     */
    fun handle(packet: Packet) {
        for(method in javaClass.methods) {
            if(method.isAnnotationPresent(PacketReceiver::class.java)) {
                val params: Array<Class<*>> = method.parameterTypes
                if(params.size == 1 && params[0] == packet.javaClass) {
                    method.invoke(this, packet)
                }
            }
        }
    }
}