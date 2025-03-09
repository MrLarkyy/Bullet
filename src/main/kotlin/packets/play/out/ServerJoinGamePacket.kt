package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.entity.player.data.GameMode
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * Packet sent to the client that sends all the information needed to join the game
 *
 * @param entityID The players entity id
 * @param hardcore If the world is hardcore or not
 * @param dimensionNames Identifiers for all dimensions on the server
 * @param viewDistance The view distance set by the server
 * @param simulationDistance The distance that the client will process specific things, such as entities.
 * @param reducedDebugInfo Whether to have reduced debug info in f3 menu
 * @param enableRespawnScreen Set to false when the doImmediateRespawn gamerule is true.
 * @param dimensionType The ID of the type of dimension in the minecraft:dimension_type registry
 * @param dimensionName The name of the dimension
 * @param gameMode The players gamemode
 * @param isFlat Whether the world is flat or not
 */
class ServerJoinGamePacket(
    var entityID: Int,
    var hardcore: Boolean,
    var dimensionNames: List<String>,
    var viewDistance: Int,
    var simulationDistance: Int,
    var reducedDebugInfo: Boolean,
    var enableRespawnScreen: Boolean,
    var dimensionType: Int,
    var dimensionName: String,
    var gameMode: GameMode,
    var isFlat: Boolean,
) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x2C, ResourceLocation.vanilla("play.out.login"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeInt(entityID)
            writeBoolean(hardcore)

            writeVarInt(dimensionNames.size)
            dimensionNames.forEach { writeString(it) }

            writeVarInt(0) // Max Players (Unused by the client)
            writeVarInt(viewDistance)
            writeVarInt(simulationDistance)
            writeBoolean(reducedDebugInfo)
            writeBoolean(enableRespawnScreen)
            writeBoolean(false) // Do limited crafting (Unused by client according to wiki)
            writeVarInt(dimensionType)
            writeString(dimensionName)
            writeLong(0L) // Hashed seed
            writeByte(gameMode.id)
            writeByte(-1) // Previous player gamemode
            writeBoolean(isFlat)
            writeBoolean(false)
            writeBoolean(false)
            writeVarInt(0)
            writeVarInt(0)
            writeBoolean(false)
        }
    }
}
