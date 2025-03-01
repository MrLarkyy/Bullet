package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import com.aznos.player.GameMode
import dev.dewy.nbt.Nbt
import dev.dewy.nbt.tags.collection.CompoundTag

/**
 * Packet sent to the client that sends all the information needed to join the game
 *
 * @param payload The long timestamp of the ping request
 */
class ServerJoinGamePacket(
    entityID: Int,
    hardcore: Boolean,
    gameMode: GameMode,
    world: String,
    codec: CompoundTag,
    maxPlayers: Int,
    viewDistance: Int,
    reducedDebugInfo: Boolean,
    enableRespawnScreen: Boolean,
    isDebug: Boolean,
    isFlat: Boolean
) : Packet(0x24) {
    init {
        wrapper.writeInt(entityID)
        wrapper.writeBoolean(hardcore)
        wrapper.writeByte(gameMode.id)
        wrapper.writeByte(-1)
        wrapper.writeVarInt(1)
        wrapper.writeString(world)

        val nbt = Nbt()
        nbt.toStream(codec, wrapper)

        val dimensionTypeList = codec.getCompound("minecraft:dimension_type")
            .getList<CompoundTag>("value")

        val overworldEntry = dimensionTypeList.first {
            it.getString("name").value == "minecraft:overworld"
        }

        val dimension = overworldEntry.getCompound("element")
        nbt.toStream(dimension, wrapper)

        wrapper.writeString(world)
        wrapper.writeLong(0)
        wrapper.writeVarInt(maxPlayers)
        wrapper.writeVarInt(viewDistance)
        wrapper.writeBoolean(reducedDebugInfo)
        wrapper.writeBoolean(enableRespawnScreen)
        wrapper.writeBoolean(isDebug)
        wrapper.writeBoolean(isFlat)
    }
}
