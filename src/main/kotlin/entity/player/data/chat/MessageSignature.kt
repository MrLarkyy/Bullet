package com.aznos.entity.player.data.chat

data class MessageSignature(
    var bytes: Collection<Byte>
) {

    class Packed private constructor(
        var id: Int,
        var fullSignature: MessageSignature?
    ) {

        constructor(id: Int) : this(id, null)
        constructor(fullSignature: MessageSignature) : this(-1, fullSignature)

    }

}