package com.aznos.packets

import com.aznos.GameState
import packets.handshake.HandshakePacket
import java.util.WeakHashMap
import java.util.concurrent.ConcurrentHashMap

/**
 * The packet registry contains all packet references by their ID
 */
object PacketRegistry {
    private val packets: MutableMap<GameState, MutableMap<Int, Class<out Packet>>> = ConcurrentHashMap()

    init {
        //HANDSHAKE
        val handshakePackets = ConcurrentHashMap<Int, Class<out Packet>>()
        handshakePackets[0x00] = HandshakePacket::class.java
        packets[GameState.HANDSHAKE] = handshakePackets

        //STATUS
        packets[GameState.STATUS] = ConcurrentHashMap()
        //LOGIN
        packets[GameState.LOGIN] = ConcurrentHashMap()
        //PLAY
        packets[GameState.PLAY] = ConcurrentHashMap()
    }

    /**
     * Get the packet by the packet ID
     *
     * @param gameState The current gamestate
     * @param id The ID of the packet
     * @return The class of the packet
     */
    fun getPacket(gameState: GameState, id: Int): Class<out Packet>? {
        return packets.getOrDefault(gameState, WeakHashMap())[id]
    }
}