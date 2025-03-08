package com.aznos.entity.player.data.chat

import com.aznos.entity.player.data.chat.filter.FilterMask
import net.kyori.adventure.text.Component
import java.time.Instant
import java.util.*

data class ChatMessage(
    var senderUUID: UUID,
    var index: Int,
    var signature: Collection<Byte>,
    var plainContent: String,
    var timestamp: Instant,
    var salt: Long,
    var lastSeenMessagesPacked: LastSeenMessages.Packed,
    var unsignedChatContent: Component?,
    var filterMask: FilterMask,
    var chatFormatting: ChatType.Bound
) {
}