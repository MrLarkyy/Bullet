package com.aznos.packets.data

import com.aznos.datatypes.VarInt.writeVarInt
import java.io.DataOutputStream
import java.util.*

data class LightData(
    val skyLightMask: BitSet,
    val blockLightMask: BitSet,
    val emptySkyLightMask: BitSet,
    val emptyBlockLightMask: BitSet,
    val skyLightArrays: List<ByteArray>,
    val blockLightArrays: List<ByteArray>
)

fun writeBitSet(out: DataOutputStream, bitSet: BitSet) {
    val longs = bitSet.toLongArray()
    out.writeVarInt(longs.size)

    for(l in longs) {
        out.writeLong(l)
    }
}

fun writeLightData(out: DataOutputStream, lightData: LightData) {
    writeBitSet(out, lightData.skyLightMask)
    writeBitSet(out, lightData.blockLightMask)
    writeBitSet(out, lightData.emptySkyLightMask)
    writeBitSet(out, lightData.emptyBlockLightMask)

    out.writeVarInt(lightData.skyLightArrays.size)
    for(array in lightData.skyLightArrays) {
        out.writeVarInt(array.size)
        out.write(array)
    }

    out.writeVarInt(lightData.blockLightArrays.size)
    for(array in lightData.blockLightArrays) {
        out.writeVarInt(array.size)
        out.write(array)
    }
}
