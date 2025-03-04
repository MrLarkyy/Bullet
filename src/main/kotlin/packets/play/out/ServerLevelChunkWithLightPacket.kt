package com.aznos.packets.play.out

import com.aznos.packets.Packet
import com.aznos.packets.data.ChunkData

/**
 * A packet from the server -> client that sends when a chunk comes into
 * the clients view distance, specifying its terrain, lighting, and block entities
 *
 * @param x The x coordinate of the chunk
 * @param z The z coordinate of the chunk
 */
class ServerLevelChunkWithLightPacket(
    x: Int,
    z: Int,
    chunkData: ChunkData,
    lightData: LightData
) : Packet(0x28) {
    init {
        wrapper.writeInt(x)
        wrapper.writeInt(z)
    }
}