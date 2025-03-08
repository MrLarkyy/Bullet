package com.aznos.packets.play.out

import com.aznos.datatypes.ComponentType.writeComponent
import com.aznos.packets.ServerPacket
import net.kyori.adventure.text.Component

class ServerSystemChatMessagePacket(
    var message: Component,
    var isActionBar: Boolean
): ServerPacket(0x73) {
    init {
        wrapper.writeComponent(message)
        wrapper.writeBoolean(isActionBar)
    }
}