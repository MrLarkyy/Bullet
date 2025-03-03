package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.packets.Packet
import com.google.gson.JsonObject

/**
 * This packet is used to disconnect the client whenever the game is in the play state
 */
class ServerPlayDisconnectPacket(
    message: String
) : Packet(0x19) {
    init {
        val jsonObj = JsonObject()
        jsonObj.addProperty("text", message)

        wrapper.writeString(jsonObj.toString())
    }
}