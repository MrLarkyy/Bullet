package com.aznos.packets

import com.aznos.packets.play.out.ServerChunkPacket
import com.aznos.Bullet
import com.aznos.ClientSession
import com.aznos.GameState
import com.aznos.commands.CommandCodes
import com.aznos.commands.CommandManager
import com.aznos.commands.CommandManager.buildCommandGraphFromDispatcher
import com.aznos.entity.player.Player
import com.aznos.events.*
import com.aznos.packets.data.ServerStatusResponse
import com.aznos.packets.login.`in`.ClientLoginStartPacket
import com.aznos.packets.login.out.ServerLoginSuccessPacket
import com.aznos.packets.play.`in`.ClientChatMessagePacket
import com.aznos.packets.play.`in`.ClientKeepAlivePacket
import com.aznos.packets.play.out.ServerJoinGamePacket
import com.aznos.packets.play.out.ServerPlayerPositionAndLookPacket
import com.aznos.packets.status.`in`.ClientStatusPingPacket
import com.aznos.packets.status.`in`.ClientStatusRequestPacket
import com.aznos.packets.status.out.ServerStatusPongPacket
import com.aznos.entity.player.data.GameMode
import com.aznos.entity.player.data.Location
import com.aznos.packets.play.`in`.movement.ClientPlayerMovement
import com.aznos.packets.play.`in`.movement.ClientPlayerPositionAndRotation
import com.aznos.packets.play.`in`.movement.ClientPlayerPositionPacket
import com.aznos.packets.play.`in`.movement.ClientPlayerRotation
import com.aznos.packets.play.out.ServerDeclareCommandsPacket
import com.aznos.packets.play.out.ServerSpawnPlayerPacket
import com.aznos.packets.play.out.movement.ServerEntityMovementPacket
import com.aznos.packets.play.out.movement.ServerEntityPositionAndRotationPacket
import com.aznos.packets.play.out.movement.ServerEntityPositionPacket
import com.aznos.packets.play.out.movement.ServerEntityRotationPacket
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import packets.handshake.HandshakePacket
import packets.status.out.ServerStatusResponsePacket
import java.util.UUID

/**
 * Handles all incoming packets by dispatching them to the appropriate handler methods
 *
 * @property client The clients session
 */
@Suppress("UnusedParameter", "TooManyFunctions")
class PacketHandler(
    private val client: ClientSession
) {
    /**
     * Every 20 ticks the client will send an empty movement packet telling the server if the
     * client is on the ground or not
     */
    @PacketReceiver
    fun onPlayerMovement(packet: ClientPlayerMovement) {
        val player = client.player
        player.onGround = packet.onGround

        for(otherPlayer in Bullet.players) {
            if(otherPlayer != player) {
                otherPlayer.clientSession.sendPacket(ServerEntityMovementPacket(player.entityID))
            }
        }
    }

    /**
     * Handles when a player rotates to a new yaw and pitch
     */
    @PacketReceiver
    fun onPlayerRotation(packet: ClientPlayerRotation) {
        val player = client.player
        player.location = Location(player.location.x, player.location.y, player.location.z, packet.yaw, packet.pitch)
        player.onGround = packet.onGround

        for(otherPlayer in Bullet.players) {
            if(otherPlayer != player) {
                otherPlayer.clientSession.sendPacket(ServerEntityRotationPacket(
                    player.entityID,
                    player.location.yaw,
                    player.location.pitch,
                    player.onGround
                ))
            }
        }
    }

    /**
     * Handles when a player moves to a new position and rotation axis at the same time
     */
    @PacketReceiver
    fun onPlayerPositionAndRotation(packet: ClientPlayerPositionAndRotation) {
        val player = client.player
        val lastLocation = player.location

        val (deltaX, deltaY, deltaZ) = calculateDeltas(
            packet.x,
            packet.feetY,
            packet.z,
            lastLocation.x,
            lastLocation.y,
            lastLocation.z
        )

        player.location = Location(packet.x, packet.feetY, packet.z, packet.yaw, packet.pitch)
        player.onGround = packet.onGround

        for(otherPlayer in Bullet.players) {
            if(otherPlayer != player) {
                otherPlayer.clientSession.sendPacket(
                    ServerEntityPositionAndRotationPacket(
                        player.entityID,
                        deltaX,
                        deltaY,
                        deltaZ,
                        player.location.yaw,
                        player.location.pitch,
                        player.onGround
                    )
                )
            }
        }
    }

    /**
     * Handles when a player moves to a new position
     */
    @PacketReceiver
    fun onPlayerPosition(packet: ClientPlayerPositionPacket) {
        val player = client.player
        val lastLocation = player.location

        val (deltaX, deltaY, deltaZ) = calculateDeltas(
            packet.x,
            packet.feetY,
            packet.z,
            lastLocation.x,
            lastLocation.y,
            lastLocation.z
        )

        player.location = Location(packet.x, packet.feetY, packet.z, player.location.yaw, player.location.pitch)
        player.onGround = packet.onGround

        for(otherPlayer in Bullet.players) {
            if(otherPlayer != player) {
                otherPlayer.clientSession.sendPacket(
                    ServerEntityPositionPacket(
                        player.entityID,
                        deltaX,
                        deltaY,
                        deltaZ,
                        player.onGround
                    )
                )
            }
        }
    }

    /**
     * Handles when a chat message is received
     */
    @PacketReceiver
    fun onChatMessage(packet: ClientChatMessagePacket) {
        val message = packet.message

        if(message.length > 255) {
            client.disconnect("Message too long")
            return
        }

        if(message.startsWith('/')) {
            val command = message.substring(1)
            val commandSource = client.player

            val result = CommandManager.dispatcher.execute(command, commandSource)
            if(result != CommandCodes.SUCCESS.id) {
                when(result) {
                    CommandCodes.UNKNOWN.id ->
                        client.sendMessage(Component.text("Unknown command")
                            .color(NamedTextColor.RED))

                    CommandCodes.ILLEGAL_ARGUMENT.id ->
                        client.sendMessage(Component.text("Invalid command syntax, try typing /help")
                            .color(NamedTextColor.RED))

                    CommandCodes.INVALID_PERMISSIONS.id ->
                        client.sendMessage(Component.text("You don't have permission to use this command")
                            .color(NamedTextColor.RED))
                }
            }

            return
        }

        val formattedMessage = message.replace('&', '§')

        val event = PlayerChatEvent(client.player.username, formattedMessage)
        EventManager.fire(event)
        if(event.isCancelled) return

        val textComponent = Component.text()
            .append(Component.text().content("<").color(NamedTextColor.GRAY))
            .append(Component.text().content(client.player.username).color(TextColor.color(0x55FFFF)))
            .append(Component.text().content("> ").color(NamedTextColor.GRAY))
            .append(Component.text().content(formattedMessage).color(TextColor.color(0xFFFFFF)))
            .build()

        client.sendMessage(textComponent)
    }

    /**
     * Handles when the client responds to the server keep alive packet to tell the server the client is still online
     */
    @PacketReceiver
    fun onKeepAlive(packet: ClientKeepAlivePacket) {
        val event = PlayerHeartbeatEvent(client.player.username)
        EventManager.fire(event)
        if(event.isCancelled) return

        client.respondedToKeepAlive = true
    }

    /**
     * Handles when the client tells the server it's ready to log in
     *
     * The server first checks for a valid version and uuid, then sends a login success packet
     * It'll then transition the game state into play mode
     * and send a join game and player position/look packet to get past all loading screens
     */
    @PacketReceiver
    fun onLoginStart(packet: ClientLoginStartPacket) {
        val preJoinEvent = PlayerPreJoinEvent()
        EventManager.fire(preJoinEvent)
        if(preJoinEvent.isCancelled) return

        if(client.protocol > Bullet.PROTOCOL) {
            client.disconnect("Please downgrade your Minecraft version to " + Bullet.VERSION)
            return
        } else if(client.protocol < Bullet.PROTOCOL) {
            client.disconnect("Your client is outdated, please upgrade to Minecraft version " + Bullet.VERSION)
            return
        }

        val username = packet.username
        if(!username.matches(Regex("^[a-zA-Z0-9]{3,16}$"))) {
            client.disconnect("Invalid username")
            return
        }

        val uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:$username").toByteArray())
        val player = initializePlayer(username, uuid)

        client.sendPacket(ServerLoginSuccessPacket(uuid, username))
        client.state = GameState.PLAY

        client.sendPacket(
            ServerJoinGamePacket(
                player.entityID,
                false,
                player.gameMode,
                "minecraft:overworld",
                Bullet.dimensionCodec!!,
                Bullet.max_players,
                8,
                reducedDebugInfo = false,
                enableRespawnScreen = true,
                isDebug = false,
                isFlat = true
            )
        )

        client.sendPacket(ServerPlayerPositionAndLookPacket(player.location))

        val joinEvent = PlayerJoinEvent(client.player.username)
        EventManager.fire(joinEvent)
        if(joinEvent.isCancelled) return

        Bullet.players.add(player)
        client.sendPlayerSpawnPacket()
        client.scheduleKeepAlive()
        client.sendPacket(ServerChunkPacket(0, 0))

        sendSpawnPlayerPackets(player)

        val (nodes, rootIndex) = buildCommandGraphFromDispatcher(CommandManager.dispatcher)
        client.sendPacket(ServerDeclareCommandsPacket(nodes, rootIndex))
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
        val event = StatusRequestEvent(Bullet.max_players, 0, Bullet.motd)
        EventManager.fire(event)
        if(event.isCancelled) return

        val response = ServerStatusResponse(
            ServerStatusResponse.Version(Bullet.VERSION, Bullet.PROTOCOL),
            ServerStatusResponse.Players(event.maxPlayers, event.onlinePlayers),
            event.motd,
            Bullet.favicon,
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

        val event = HandshakeEvent(client.state, client.protocol)
        EventManager.fire(event)
        if(event.isCancelled) return
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

    private fun calculateDeltas(
        currentX: Double, currentY: Double, currentZ: Double,
        lastX: Double, lastY: Double, lastZ: Double
    ): Triple<Short, Short, Short> {
        val deltaX = ((currentX - lastX) * 4096).toInt().coerceIn(-32768, 32767).toShort()
        val deltaY = ((currentY - lastY) * 4096).toInt().coerceIn(-32768, 32767).toShort()
        val deltaZ = ((currentZ - lastZ) * 4096).toInt().coerceIn(-32768, 32767).toShort()
        return Triple(deltaX, deltaY, deltaZ)
    }

    private fun initializePlayer(username: String, uuid: UUID): Player {
        val player = Player(client)
        player.username = username
        player.uuid = uuid
        player.location = Location(8.5, 2.0, 8.5, 0f, 0f)
        player.gameMode = GameMode.CREATIVE
        player.onGround = false

        client.player = player
        return player
    }

    private fun sendSpawnPlayerPackets(player: Player) {
        for(otherPlayer in Bullet.players) {
            if(otherPlayer != player) {
                otherPlayer.clientSession.sendPacket(
                    ServerSpawnPlayerPacket(
                        player.entityID,
                        player.uuid,
                        player.location.x,
                        player.location.y,
                        player.location.z,
                        player.location.yaw,
                        player.location.pitch
                    )
                )
            }
        }

        for(existingPlayer in Bullet.players) {
            if(existingPlayer != player) {
                client.sendPacket(
                    ServerSpawnPlayerPacket(
                        existingPlayer.entityID,
                        existingPlayer.uuid,
                        existingPlayer.location.x,
                        existingPlayer.location.y,
                        existingPlayer.location.z,
                        existingPlayer.location.yaw,
                        existingPlayer.location.pitch
                    )
                )
            }
        }
    }
}