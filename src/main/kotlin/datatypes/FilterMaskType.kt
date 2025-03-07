package com.aznos.datatypes

import com.aznos.datatypes.BitSetType.readBitSet
import com.aznos.datatypes.BitSetType.writeBitSet
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.entity.player.data.chat.filter.FilterMask
import com.aznos.entity.player.data.chat.filter.FilterMaskType
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object FilterMaskType {

    @Throws(IOException::class)
    fun DataInputStream.readFilterMask(): FilterMask {
        val type = FilterMaskType.byId(readVarInt())!!
        return when (type) {
            FilterMaskType.PARTIALLY_FILTERED -> FilterMask(readBitSet())
            FilterMaskType.PASS_THROUGH -> FilterMask.PASS_THROUGH
            FilterMaskType.FULLY_FILTERED -> FilterMask.FULLY_FILTERED
        }
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeFilterMask(filterMask: FilterMask) {
        writeVarInt(filterMask.type.id)
        if (filterMask.type == FilterMaskType.PARTIALLY_FILTERED) {
            writeBitSet(filterMask.mask)
        }
    }

}