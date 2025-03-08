package com.aznos.packets.data.crypto

import java.security.PublicKey
import java.time.Instant

class SignatureData(
    val timestamp: Instant,
    val publicKey: PublicKey,
    val signature: Collection<Byte>
) {
}