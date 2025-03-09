package com.aznos.packets.play.out

import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import net.kyori.adventure.text.Component

/**
 * This packet is used to disconnect the client whenever the game is in the play state
 */
class ServerPlayDisconnectPacket(
    var message: Component
) : com.aznos.packets.newPacket.ServerPacket(key) {

    companion object {
        val key = Keyed(0x1D, ResourceLocation.vanilla("play.out.disconnect"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeComponent(message)
        }
    }
}