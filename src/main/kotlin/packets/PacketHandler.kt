package com.aznos.packets

import com.aznos.Bullet
import com.aznos.ClientSession
import com.aznos.GameState
import com.aznos.packets.configuration.out.ServerConfigFinishPacket
import com.aznos.packets.configuration.out.ServerConfigRegistryData
import com.aznos.packets.data.ServerStatusResponse
import com.aznos.packets.login.`in`.ClientLoginStartPacket
import com.aznos.packets.login.out.ServerLoginDisconnectPacket
import com.aznos.packets.login.out.ServerLoginSuccessPacket
import com.aznos.packets.play.`in`.ClientKeepAlivePacket
import com.aznos.packets.play.out.ServerJoinGamePacket
import com.aznos.packets.play.out.ServerSyncPlayerPosition
import com.aznos.packets.status.`in`.ClientStatusPingPacket
import com.aznos.packets.status.`in`.ClientStatusRequestPacket
import com.aznos.packets.status.out.ServerStatusPongPacket
import com.aznos.player.GameMode
import dev.dewy.nbt.tags.collection.CompoundTag
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
     * Handles when the client responds to the server keep alive packet to tell the server the client is still online
     */
    @PacketReceiver
    fun onKeepAlive(packet: ClientKeepAlivePacket) {

    }

    /**
     * Handles when the client tells the server it's ready to log in
     *
     * The server first checks for a valid version and uuid, then sends a login success packet
     * It'll then transition the game state into play mode, and send a join game and player position/look packet to get past all loading screens
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
        client.state = GameState.CONFIGURATION

        client.sendPacket(ServerConfigRegistryData("minecraft:dimension_type", listOf(
            ServerConfigRegistryData.Entry("minecraft:overworld", CompoundTag().apply {
                putByte("has_skylight", 0)
                putByte("has_ceiling", 0)
                putByte("ultrawarm", 0)
                putByte("natural", 0)
                putDouble("coordinate_scale", 1.0)
                putByte("bed_works", 0)
                putByte("respawn_anchor_works", 0)
                putInt("min_y", -64)
                putInt("height", 320)
                putInt("logical_height", 0)
                putString("infiniburn", "#minecraft:infiniburn_overworld")
                putString("effects", "minecraft:overworld")
                putFloat("ambient_light", 0f)
                putByte("piglin_safe", 0)
                putByte("has_raids", 0)
                putInt("monster_spawn_light_level", 0)
                putInt("monster_spawn_block_light_limit", 0)
            })
        )))

        client.sendPacket(ServerConfigRegistryData("minecraft:worldgen/biome", listOf(
            ServerConfigRegistryData.Entry("minecraft:plains", CompoundTag().apply {
                putByte("has_precipitation", 0)
                putFloat("temperature", 0f)
                putFloat("downfall", 0f)
                put<CompoundTag>("effects", CompoundTag().apply {
                    putInt("fog_color", 0)
                    putInt("water_color", 0)
                    putInt("water_fog_color", 0)
                    putInt("sky_color", 0)
                })
            })
        )))

        client.sendPacket(ServerConfigRegistryData("minecraft:wolf_variant", listOf(
            ServerConfigRegistryData.Entry("minecraft:black", CompoundTag().apply {
                putString("angry_texture", "minecraft:entity/wolf/wolf_black_angry")
                putString("biomes", "minecraft:plains")
                putString("tame_texture", "minecraft:entity/wolf/wolf_black_tame")
                putString("wild_texture", "minecraft:entity/wolf/wolf_black")
            })
        )))

        client.sendPacket(ServerConfigRegistryData("minecraft:painting_variant", listOf(
            ServerConfigRegistryData.Entry("minecraft:alban", CompoundTag().apply {
                putString("asset_id", "minecraft:alban")
                putInt("height", 1)
                putInt("width", 1)
                putString("title", "gg")
                putString("author", "gg")
            })
        )))

        client.sendPacket(ServerConfigRegistryData("minecraft:damage_type", listOf(
            ServerConfigRegistryData.Entry("minecraft:in_fire", CompoundTag().apply {
                putString("effects", "burning")
                putFloat("exhaustion", 0.1f)
                putString("scaling", "when_caused_by_living_non_player")
                putString("message_id", "inFire")
            })
        )))

        client.sendPacket(ServerConfigRegistryData("minecraft:damage_type", listOf(
            ServerConfigRegistryData.Entry("minecraft:campfire", CompoundTag().apply {
                putString("effects", "burning")
                putFloat("exhaustion", 0.1f)
                putString("scaling", "when_caused_by_living_non_player")
                putString("message_id", "inFire")
            })
        )))
        
        client.sendPacket(ServerConfigFinishPacket())
        client.state = GameState.PLAY

        client.sendPacket(ServerJoinGamePacket(
            0,
            false,
            listOf("minecraft:overworld"),
            8,
            8,
            false,
            true,
            0,
            "minecraft:overworld",
            GameMode.CREATIVE,
            false
        ))

        client.sendPacket(ServerSyncPlayerPosition(0, 0.0, 0.0, 0.0, 0.0, 5.0, 0.0, 0f, 90f))
        client.scheduleKeepAlive()
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