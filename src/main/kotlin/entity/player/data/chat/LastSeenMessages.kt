package com.aznos.entity.player.data.chat

import java.util.BitSet
import java.util.UUID

class LastSeenMessages(
    val entries: List<Entry>,
) {

    data class Packed(
        var packedMessageSignatures: List<MessageSignature.Packed>
    ) {
    }

    data class Entry(
        val uuid: UUID,
        val signature: Collection<Byte>
    )

    data class Update(
        val offset: Int,
        val acknowledged: BitSet
    )

}