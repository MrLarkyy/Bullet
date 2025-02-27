package packets.status.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.packets.Packet

/**
 * Packet for sending the server status response to the client status request packet
 *
 * @param json The JSON string of the server status
 */
class ServerStatusResponsePacket(json: String) : Packet(0x00) {
    init {
        wrapper.writeString(json)
    }
}
