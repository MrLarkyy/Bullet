package com.aznos.entity.player.data.chat.filter

enum class FilterMaskType {

    PASS_THROUGH,
    FULLY_FILTERED,
    PARTIALLY_FILTERED;

    val id: Int
        get() {
            return ordinal
        }

    companion object {
        fun byId(id: Int): FilterMaskType? = entries.getOrNull(id)
    }
}