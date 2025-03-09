package packets.status.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket

/**
 * Packet for sending the server status response to the client status request packet
 *
 * @param json The JSON string of the server status
 */
class ServerStatusResponsePacket(var json: String) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x00, ResourceLocation.vanilla("status.out.status_response"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeString(json)
        }
    }
}
