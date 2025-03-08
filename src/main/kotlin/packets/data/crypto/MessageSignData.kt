package com.aznos.packets.data.crypto

import java.time.Instant

class MessageSignData(
    val saltSignature: SaltSignature,
    val timestamp: Instant,
    var signedPreview: Boolean = false
) {
}