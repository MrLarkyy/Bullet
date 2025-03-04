package com.aznos.packets.play.out

import com.aznos.packets.Packet
import com.aznos.packets.data.ChunkData
import com.aznos.packets.data.LightData
import com.aznos.packets.data.writeChunkData
import com.aznos.packets.data.writeLightData
import dev.dewy.nbt.api.registry.TagTypeRegistry

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
    lightData: LightData,
    registry: TagTypeRegistry
) : Packet(0x28) {
    init {
        wrapper.writeInt(x)
        wrapper.writeInt(z)

        writeChunkData(wrapper, chunkData, registry)
        writeLightData(wrapper, lightData)
    }
}