package com.aznos.packets.login.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.packets.Packet
import com.google.gson.JsonObject

/**
 * Packet representing a status request from the client
 * The server sends back a server status response packet
 */
class ServerLoginDisconnectPacket(message: String) : Packet(0x00) {
    init {
        val jsonObj = JsonObject()
        jsonObj.addProperty("text", message)

        wrapper.writeString(jsonObj.toString())
    }
}
