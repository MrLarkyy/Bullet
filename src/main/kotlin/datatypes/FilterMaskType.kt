package com.aznos.datatypes

import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.entity.player.data.chat.filter.FilterMask
import com.aznos.entity.player.data.chat.filter.FilterMaskType
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.time.Instant

object FilterMaskType {

    @Throws(IOException::class)
    fun DataInputStream.readFilterMask(): FilterMask {
        val type = FilterMaskType.byId(readVarInt())!!
        when(type) {
            FilterMaskType.PARTIALLY_FILTERED ->return FilterMask(readBit)
        }
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeFilterMask(filterMask: FilterMask) {
        writeLong(instant.toEpochMilli())
    }

}