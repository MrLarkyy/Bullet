package com.aznos.datatypes

import com.aznos.datatypes.CollectionType.readCollection
import com.aznos.datatypes.CollectionType.writeCollection
import com.aznos.datatypes.MessageSignaturePackedType.readMessageSignaturePacked
import com.aznos.datatypes.MessageSignaturePackedType.writeMessageSignaturePacked
import com.aznos.entity.player.data.chat.LastSeenMessages
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object LastSeenMessagesPacked {

    @Throws(IOException::class)
    fun DataInputStream.readLastSeenMessagesPacked(): LastSeenMessages.Packed {
        val list = readCollection( { i -> ArrayList(i)}, { os -> os.readMessageSignaturePacked()})
        return LastSeenMessages.Packed(list)
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeLastSeenMessagesPacked(lastSeenMessagesPacked: LastSeenMessages.Packed) {
        writeCollection(lastSeenMessagesPacked.packedMessageSignatures) { os, v ->
            os.writeMessageSignaturePacked(v)
        }
    }

}