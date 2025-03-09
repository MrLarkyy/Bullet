package com.aznos.packets.data.chunk

import com.aznos.datatypes.CollectionType.readCollection
import com.aznos.datatypes.LongArrayType.readLongArray
import com.aznos.datatypes.LongArrayType.writeLongArray
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.BitSet

class LightData(
    var blockLightMask: BitSet,
    var skyLightMask: BitSet,
    var emptyBlockLightMask: BitSet,
    var emptySkyLightMask: BitSet,
    skyLightArray: Array<ByteArray>,
    blockLightArray: Array<ByteArray>
) {

    val skyLightCount: Int
        get() {
            return skyLightMask.size()
        }
    val blockLightCount: Int
        get() {
            return blockLightMask.size()
        }

    var skyLightArray: Array<ByteArray> = skyLightArray
        private set
    var blockLightArray: Array<ByteArray> = blockLightArray
        private set

    companion object {
        fun read(input: DataInputStream): LightData {
            val skyLightMask = BitSet.valueOf(input.readLongArray())
            val blockLightMask = BitSet.valueOf(input.readLongArray())

            val emptySkyLightMask = BitSet.valueOf(input.readLongArray())
            val emptyBlockLightMask = BitSet.valueOf(input.readLongArray())

            val skyLightCount = input.readVarInt()
            val skyLightArray = Array<ByteArray>(skyLightCount) {
                val size = input.readVarInt()
                input.readNBytes(size)
            }

            val blockLightCount = input.readShort().toInt()
            val blockLightArray = Array(blockLightCount) {
                val size = input.readVarInt()
                input.readNBytes(size)
            }
            return LightData(
                skyLightMask,
                blockLightMask,
                emptySkyLightMask,
                emptyBlockLightMask,
                skyLightArray,
                blockLightArray
            )
        }

        fun write(output: DataOutputStream, lightData: LightData) {
            output.writeLongArray(lightData.skyLightMask.toLongArray())
            output.writeLongArray(lightData.blockLightMask.toLongArray())
            output.writeLongArray(lightData.emptySkyLightMask.toLongArray())
            output.writeLongArray(lightData.emptyBlockLightMask.toLongArray())

            output.writeVarInt(lightData.skyLightArray.size)
            for (i in 0..<lightData.skyLightCount) {
                output.writeVarInt(lightData.skyLightArray[i].size)
                output.write(lightData.skyLightArray[i])
            }
            output.writeVarInt(lightData.blockLightArray.size)
            for (i in 0..<lightData.blockLightCount) {
                output.writeVarInt(lightData.blockLightArray[i].size)
                output.write(lightData.blockLightArray[i])
            }
        }
    }
}