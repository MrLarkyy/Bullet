package com.aznos.entity.player.data.chat.filter

import java.util.BitSet

data class FilterMask(
    val mask: BitSet,
    val type: FilterMaskType = FilterMaskType.PARTIALLY_FILTERED
) {

    companion object {
        val FULLY_FILTERED = FilterMask(BitSet(0), FilterMaskType.FULLY_FILTERED)
        val PASS_THROUGH = FilterMask(BitSet(0), FilterMaskType.PASS_THROUGH)
    }

}