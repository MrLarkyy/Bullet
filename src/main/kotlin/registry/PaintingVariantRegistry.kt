package com.aznos.registry

import com.aznos.data.PaintingVariants
import com.aznos.datatypes.NBTType
import com.aznos.packets.newPacket.ResourceLocation
import dev.dewy.nbt.tags.collection.CompoundTag
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

class PaintingVariantRegistry : Registry<PaintingVariantRegistry.PaintingVariant>(ResourceLocation.vanilla("painting_variant")) {

    init {
        for (entry in PaintingVariants.entries) {
            register(ResourceLocation.vanilla(entry.name), PaintingVariant(
                entry.assetId,
                entry.height,
                entry.width,
                entry.title?.let { CompoundTag().fromJson(GsonComponentSerializer.gson().serializeToTree(GsonComponentSerializer.gson().deserialize(it)).asJsonObject, 0, NBTType.tagTypeRegistry) },
                entry.author?.let { CompoundTag().fromJson(GsonComponentSerializer.gson().serializeToTree(GsonComponentSerializer.gson().deserialize(it)).asJsonObject, 0, NBTType.tagTypeRegistry) },
            ))
        }
    }

    override fun asCompound(value: PaintingVariant): CompoundTag {
        val result = CompoundTag()

        result.putString("asset_id", value.assetId)
        result.putInt("height", value.height)
        result.putInt("width", value.width)
        value.title?.let { result.put<CompoundTag>("title", it) }
        value.author?.let { result.put<CompoundTag>("author", it) }

        return result
    }

    data class PaintingVariant(
        val assetId: String,
        val height: Int,
        val width: Int,
        val title: CompoundTag?,
        val author: CompoundTag?
    )
}
