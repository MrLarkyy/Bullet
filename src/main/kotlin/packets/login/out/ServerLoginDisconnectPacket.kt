package com.aznos.packets.login.out

import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket
import net.kyori.adventure.text.Component

/**
 * This packet is sent when you want to disconnect the client during the LOGIN phase
 *
 * @param message The reason for the disconnection
 */
class ServerLoginDisconnectPacket(
    var message: Component
) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x00, ResourceLocation.vanilla("play.out.login_disconnect"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeComponent(message)
        }
    }
}
