package com.aznos.packets.play.out.chat

import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import net.kyori.adventure.text.Component

class ServerSystemChatMessagePacket(
    var message: Component,
    var isActionBar: Boolean
): com.aznos.packets.newPacket.ServerPacket(key) {

    companion object {
        val key = Keyed(0x73, ResourceLocation.vanilla("play.out.system_chat"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeComponent(message)
            writeBoolean(isActionBar)
        }
    }
}