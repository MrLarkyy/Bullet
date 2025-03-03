package com.aznos.packets.configuration.out

import com.aznos.datatypes.NBTType.writeNbtCompound
import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import dev.dewy.nbt.tags.collection.CompoundTag

/**
 * This packet is sent when you want to apply registries to the client
*/
class ServerConfigRegistryData(
    registryId: String,
    entries: List<Entry>
) : Packet(0x07) {
    init {
        wrapper.writeString(registryId)
        wrapper.writeVarInt(entries.size)

        for (entry in entries) {
            wrapper.writeString(entry.name)
            wrapper.writeBoolean(true)
            wrapper.writeNbtCompound(entry.tag)
        }
    }

    data class Entry(val name: String, val tag: CompoundTag)
}