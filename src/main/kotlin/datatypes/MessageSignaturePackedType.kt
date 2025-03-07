package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.entity.player.data.chat.MessageSignature
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object MessageSignaturePackedType {

    @Throws(IOException::class)
    fun DataInputStream.readMessageSignaturePacked(): MessageSignature.Packed {
        val id = readVarInt() - 1
        if (id == -1) {
            return MessageSignature.Packed(MessageSignature(readNBytes(256).toList()))
        }
        return MessageSignature.Packed(id)
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeMessageSignaturePacked(messageSignaturePacked: MessageSignature.Packed) {
        writeVarInt(messageSignaturePacked.id + 1)
        if (messageSignaturePacked.fullSignature != null) {
            write(messageSignaturePacked.fullSignature!!.bytes.toByteArray())
        }
    }

}