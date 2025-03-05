package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import com.aznos.packets.data.PlayerInfo

/**
 * Sent by the server to update the player list (TAB menu)
 *
 * @param action The action type
 * (0: Add, 1: Update Gamemode, 2: Update Ping, 3: Update Display Name, 4: Remove)
 * @param players List of player info entries
 */
class ServerPlayerInfoPacket(
    private val action: Int,
    private val players: List<PlayerInfo>
) : Packet(0x32) {
    init {
        wrapper.writeVarInt(action)
        wrapper.writeVarInt(players.size)

        for(player in players) {
            wrapper.writeUUID(player.uuid)
            when(action) {
                0 -> { //Add player
                    wrapper.writeString(player.username)
                    wrapper.writeVarInt(player.properties.size)

                    for(property in player.properties) {
                        wrapper.writeString(property.name)
                        wrapper.writeString(property.value)
                        wrapper.writeBoolean(property.isSigned)
                        if(property.isSigned) {
                            wrapper.writeString(property.signature ?: "")
                        }
                    }

                    wrapper.writeVarInt(player.gameMode.id)
                    wrapper.writeVarInt(player.ping)
                    wrapper.writeBoolean(player.hasDisplayName)
                    if(player.hasDisplayName) {
                        wrapper.writeString(player.displayName ?: "")
                    }
                }

                1 -> {  //Update Gamemode
                    wrapper.writeVarInt(player.gameMode.id)
                }

                2 -> { //Update Ping
                    wrapper.writeVarInt(player.ping)
                }

                3 -> { //Update Display Name
                    wrapper.writeBoolean(player.hasDisplayName)
                    if(player.hasDisplayName) {
                        wrapper.writeString(player.displayName ?: "")
                    }
                }

                4 -> { //Remove player

                }
            }
        }
    }
}