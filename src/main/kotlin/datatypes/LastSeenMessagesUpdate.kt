package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.entity.player.data.chat.LastSeenMessages
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.*

object LastSeenMessagesUpdate {

    @Throws(IOException::class)
    fun DataInputStream.readLastSeenMessagesUpdate(): LastSeenMessages.Update {
        val signedMessages = readVarInt()
        val seen: BitSet = BitSet.valueOf(readNBytes(3))
        return LastSeenMessages.Update(signedMessages, seen)
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeLastSeenMessagesUpdate(update: LastSeenMessages.Update) {
        writeVarInt(update.offset)
        val lastSeen = Arrays.copyOf(update.acknowledged.toByteArray(), 3)
        write(lastSeen)
    }

}