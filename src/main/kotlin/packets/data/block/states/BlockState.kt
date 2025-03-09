package com.aznos.packets.data.block.states

import com.aznos.packets.data.block.states.type.StateType
import com.aznos.packets.data.block.states.type.StateValue

class BlockState(
    val type: StateType,
    data: List<String>?,
    val globalId: Int,
    val mappingsIndex: Byte
) {

    private val data: MutableMap<StateValue, Any> = HashMap(0)

    init {
        if (data != null) {
            for (s in data) {
                try {
                    val split = s.split("=")
                    val value = StateValue.byName(split[0])!!
                    this.data[value] = value.parser(split[1].uppercase())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}