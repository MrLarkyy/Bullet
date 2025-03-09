package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.PlayerInfo
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * Sent by the server to update the player list (TAB menu)
 *
 * @param action The action type
 * (0: Add, 1: Update Gamemode, 2: Update Ping, 3: Update Display Name, 4: Remove)
 * @param players List of player info entries
 */
class ServerPlayerInfoUpdatePacket(
    var action: Int,
    var players: List<PlayerInfo>
) : ServerPacket(Keyed(0x40, ResourceLocation.vanilla("player_info_update"))) {

    override fun retrieveData(): ByteArray {
        return writeData {
            writeVarInt(action)
            writeVarInt(players.size)

            for(player in players) {
                writeUUID(player.uuid)
                when(action) {
                    0 -> { //Add player
                        writeString(player.username)
                        writeVarInt(player.properties.size)

                        for(property in player.properties) {
                            writeString(property.name)
                            writeString(property.value)
                            writeBoolean(property.isSigned)
                            if(property.isSigned) {
                                writeString(property.signature ?: "")
                            }
                        }

                        writeVarInt(player.gameMode.id)
                        writeVarInt(player.ping)
                        writeBoolean(player.hasDisplayName)
                        if(player.hasDisplayName) {
                            writeString(player.displayName ?: "")
                        }
                    }

                    1 -> {  //Update Gamemode
                        writeVarInt(player.gameMode.id)
                    }

                    2 -> { //Update Ping
                        writeVarInt(player.ping)
                    }

                    3 -> { //Update Display Name
                        writeBoolean(player.hasDisplayName)
                        if(player.hasDisplayName) {
                            writeString(player.displayName ?: "")
                        }
                    }

                    4 -> { //Remove player

                    }
                }
            }
        }
    }
}