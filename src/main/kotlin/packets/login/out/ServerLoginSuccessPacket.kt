package com.aznos.packets.login.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket
import java.util.*

/**
 * This packet is sent once the server receives the client login start packet and
 * confirms there are no issues with the login
 *
 * @param uuid The UUID of the player who joined
 * @param username The username of the player who joined
 */
class ServerLoginSuccessPacket(var uuid: UUID, var username: String) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x02, ResourceLocation.vanilla("play.out.login_finished"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeUUID(uuid)
            writeString(username)
            writeVarInt(0)
        }
    }
}
