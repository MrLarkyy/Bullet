package packets.handshake

import com.aznos.datatypes.StringType.readString
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.packets.Packet
import java.io.DataInputStream

/**
 * Packet representing a handshake from the client
 *
 * @property protocol The protocol version used by the client
 * @property host The IP address provided by the client
 * @property port The server port provided by the client
 * @property state The next state (1 = ping, 2 = login, 3 = transfer)
 */
class HandshakePacket(data: ByteArray) : Packet(data) {
    var protocol: Int? = null
    private var host: String? = null
    private var port: Short? = null
    var state: Int? = null

    init {
        val input: DataInputStream = getIStream()

        protocol = input.readVarInt()
        host = input.readString()
        port = input.readShort()
        state = input.readVarInt()
    }
}
