package com.aznos.packets.data.crypto

data class SaltSignature(
    var salt: Long,
    var signature: Collection<Byte>
) {
}