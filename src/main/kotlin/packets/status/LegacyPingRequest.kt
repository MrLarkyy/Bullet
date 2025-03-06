package com.aznos.packets.status

import com.aznos.Bullet
import com.aznos.ClientSession
import java.io.OutputStream

object LegacyPingRequest {

    /**
     * Handles a legacy ping request from Minecraft 1.6 clients.
     * Response format: §1<null>protocol<null>version<null>MOTD<null>online<null>max.
     */
    fun handle16Ping(out: OutputStream, session: ClientSession) {
        Bullet.logger.info("Handling legacy ping for 1.6 clients")
        val response = "\u00A71\u0000${Bullet.PROTOCOL}" +
                "\u0000${Bullet.VERSION}" +
                "\u0000${Bullet.DESCRIPTION}" +
                "\u0000${Bullet.players.size}" +
                "\u0000${Bullet.MAX_PLAYERS}"
        val responseData = encodeLegacyKickPacket(response)
        out.write(responseData)
        out.flush()
        session.close()
    }

    /**
     * Handles a legacy ping request from Beta (1.3–1.5) clients.
     * Response format: MOTD§online§max.
     */
    fun handleBetaPing(out: OutputStream, session: ClientSession) {
        Bullet.logger.info("Handling legacy ping for Beta clients")
        val response = "${Bullet.DESCRIPTION}\u00A7${Bullet.players.size}\u00A7${Bullet.MAX_PLAYERS}"
        val responseData = encodeLegacyKickPacket(response)
        out.write(responseData)
        out.flush()
        session.close()
    }

    /**
     * Encodes a legacy kick packet (ID 0xFF) with the given message.
     * The message is encoded as UTF-16BE and prefixed with a two-byte character length.
     */
    private fun encodeLegacyKickPacket(message: String): ByteArray {
        val messageBytes = message.toByteArray(Charsets.UTF_16BE)
        // The packet starts with 0xFF, followed by a 2-byte short (big-endian) indicating the length in characters.
        val length = message.length
        val response = ByteArray(3 + messageBytes.size)
        response[0] = 0xFF.toByte() // Packet ID for legacy kick
        response[1] = (length shr 8).toByte() // High byte of length
        response[2] = (length and 0xFF).toByte() // Low byte of length
        System.arraycopy(messageBytes, 0, response, 3, messageBytes.size)
        return response
    }
}