package packets.handshake

import com.aznos.datatypes.StringType.readString
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.packets.Packet
import java.io.DataInputStream

class HandshakePacket(data: ByteArray) : Packet(data) {
    private var protocol: Int? = null
    private var state: Int? = null
    private var host: String? = null
    private var port: Int? = null

    init {
        val input: DataInputStream = getIStream()

        protocol = input.readVarInt()
        host = input.readString()
        port = input.readShort().toInt()
        state = input.readByte().toInt()
    }
}
