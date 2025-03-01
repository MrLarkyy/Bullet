package com.aznos.packets.login.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.packets.Packet
import com.google.gson.JsonObject

/**
 * This packet is sent when you want to disconnect the client during the LOGIN phase
 *
 * @param message The reason for the disconnection
*/
class ServerLoginDisconnectPacket(message: String) : Packet(0x00) {
    init {
        val jsonObj = JsonObject()
        jsonObj.addProperty("text", message)

        wrapper.writeString(jsonObj.toString())
    }
}
