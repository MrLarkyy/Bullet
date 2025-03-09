package com.aznos.packets.play.out

import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import java.util.*

class ServerBossBarPacket(
    var uuid: UUID,
    var action: Action,
    var title: Component,
    var health: Float,
    var color: BossBar.Color,
    var overlay: BossBar.Overlay,
    var flags: EnumSet<BossBar.Flag>
): ServerPacket(key) {

    companion object {
        val key = Keyed(0x0A,ResourceLocation.vanilla("play.out.boss_event"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeUUID(uuid)
            writeVarInt(action.ordinal)
            when (action) {
                Action.ADD -> {
                    writeComponent(title)
                    writeFloat(health)
                    writeVarInt(color.ordinal)
                    writeVarInt(overlay.ordinal)
                    writeByte(convertFlagsToBytes().toInt())
                }
                Action.REMOVE -> {
                    return@writeData
                }
                Action.UPDATE_HEALTH -> {
                    writeFloat(health)
                }
                Action.UPDATE_TITLE -> {
                    writeComponent(title)
                }
                Action.UPDATE_STYLE -> {
                    writeVarInt(color.ordinal)
                    writeVarInt(overlay.ordinal)
                }
                Action.UPDATE_FLAGS -> {
                    writeByte(convertFlagsToBytes().toInt())
                }
            }
        }
    }

    private fun convertFlagsToBytes(): Byte {
        var bitmask = 0
        for (flag in flags) {
            val id = when (flag) {
                BossBar.Flag.DARKEN_SCREEN -> 1
                BossBar.Flag.PLAY_BOSS_MUSIC -> 2
                BossBar.Flag.CREATE_WORLD_FOG -> 4
                else -> 0
            }
            bitmask = bitmask or id
        }
        return bitmask.toByte()
    }

    enum class Action {
        ADD, REMOVE, UPDATE_HEALTH, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS;
    }

}