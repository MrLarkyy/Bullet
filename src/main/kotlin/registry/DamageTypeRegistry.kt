package com.aznos.registry

import com.aznos.data.DamageTypes
import com.aznos.packets.newPacket.ResourceLocation
import dev.dewy.nbt.tags.collection.CompoundTag

class DamageTypeRegistry : Registry<DamageTypes>(ResourceLocation.vanilla("damage_type")) {

    init {
        for (entry in DamageTypes.entries) {
            register(ResourceLocation.vanilla(entry.messageId), entry)
        }
    }

    override fun asCompound(value: DamageTypes): CompoundTag {
        val result = CompoundTag()

        result.putString("message_id", value.messageId)
        result.putString("scaling", "never")
        result.putFloat("exhaustion", 0f)

        return result
    }

}