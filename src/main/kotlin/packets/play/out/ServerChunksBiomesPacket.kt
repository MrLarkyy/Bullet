package com.aznos.packets.play.out

import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.data.chunk.Chunk
import com.aznos.packets.data.chunk.ChunkColumn
import com.aznos.packets.data.chunk.LightData
import com.aznos.packets.newPacket.Keyed
import com.aznos.packets.newPacket.ResourceLocation
import com.aznos.packets.newPacket.ServerPacket
import dev.dewy.nbt.Nbt
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

class ServerChunksBiomesPacket(
    var column: ChunkColumn,
    var lightData: LightData? = null,
    //var ignoreOldData: Boolean = false
) : ServerPacket(key) {

    companion object {
        val key = Keyed(0x0E, ResourceLocation.vanilla("play.out.chunks_biomes"))
    }

    override fun retrieveData(): ByteArray {
        return writeData {
            writeInt(column.x)
            writeInt(column.z)

            val dataBytes = ByteArrayOutputStream()
            val dataOut = DataOutputStream(dataBytes)

            val chunks = column.chunks
            for (chunk in chunks) {
                Chunk.write(dataOut, chunk)
            }

            Nbt().toStream(column.heightMaps, this)

            val data = dataBytes.toByteArray()
            writeVarInt(data.size)
            write(data)

            if (column.hasBiomeData) {
                val biomeDataBytes = ByteArray(256)
                val biomeData: IntArray = column.biomeDataInts ?: IntArray(256)
                for (i in biomeDataBytes.indices) {
                    biomeDataBytes[i] = biomeData[i].toByte()
                }
                writeVarInt(biomeDataBytes.size)
                write(biomeDataBytes)
            }

            writeVarInt(column.tileEntities.size)
            for (tileEntity in column.tileEntities) {
                writeByte(tileEntity.packedByte.toInt())
                writeShort(tileEntity.y)
                writeVarInt(tileEntity.type)
                Nbt().toStream(tileEntity.data, this)
            }

            lightData?.let {
                LightData.write(this, it)
            }
        }

    }

}