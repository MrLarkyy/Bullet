package com.aznos.registry

import com.aznos.packets.newPacket.ResourceLocation
import dev.dewy.nbt.tags.collection.CompoundTag

class WolfVariantTypes : Registry<WolfVariantType>(ResourceLocation.vanilla("wolf_variant")) {

    val ashen = register(
        ResourceLocation.vanilla("ashen"), WolfVariantType(
        "minecraft:entity/wolf/wolf_ashen",
        "minecraft:entity/wolf/wolf_ashen_tame",
        "minecraft:entity/wolf/wolf_ashen_angry"
    ))

    val black = register(
        ResourceLocation.vanilla("black"), WolfVariantType(
        "minecraft:entity/wolf/wolf_black",
        "minecraft:entity/wolf/wolf_black_tame",
        "minecraft:entity/wolf/wolf_black_angry"
    ))

    val chestnut = register(
        ResourceLocation.vanilla("chestnut"), WolfVariantType(
        "minecraft:entity/wolf/wolf_chestnut",
        "minecraft:entity/wolf/wolf_chestnut_tame",
        "minecraft:entity/wolf/wolf_chestnut_angry"
    ))

    val pale = register(
        ResourceLocation.vanilla("pale"), WolfVariantType(
        "minecraft:entity/wolf/wolf_pale",
        "minecraft:entity/wolf/wolf_pale_tame",
        "minecraft:entity/wolf/wolf_pale_angry"
    ))

    val rusty = register(
        ResourceLocation.vanilla("rusty"), WolfVariantType(
        "minecraft:entity/wolf/wolf_rusty",
        "minecraft:entity/wolf/wolf_rusty_tame",
        "minecraft:entity/wolf/wolf_rusty_angry"
    ))

    val snowy = register(
        ResourceLocation.vanilla("snowy"), WolfVariantType(
        "minecraft:entity/wolf/wolf_snowy",
        "minecraft:entity/wolf/wolf_snowy_tame",
        "minecraft:entity/wolf/wolf_snowy_angry"
    ))

    val spotted = register(
        ResourceLocation.vanilla("spotted"), WolfVariantType(
        "minecraft:entity/wolf/wolf_spotted",
        "minecraft:entity/wolf/wolf_spotted_tame",
        "minecraft:entity/wolf/wolf_spotted_angry"
    ))

    val striped = register(
        ResourceLocation.vanilla("striped"), WolfVariantType(
        "minecraft:entity/wolf/wolf_striped",
        "minecraft:entity/wolf/wolf_striped_tame",
        "minecraft:entity/wolf/wolf_striped_angry"
    ))

    val woods = register(
        ResourceLocation.vanilla("woods"), WolfVariantType(
        "minecraft:entity/wolf/wolf_woods",
        "minecraft:entity/wolf/wolf_woods_tame",
        "minecraft:entity/wolf/wolf_woods_angry"
    ))

    override fun asCompound(value: WolfVariantType): CompoundTag {
        val result = CompoundTag()

        result.putString("wild_texture", value.wildTexture)
        result.putString("tame_texture", value.tameTexture)
        result.putString("angry_texture", value.angryTexture)
        result.putString("biomes", "minecraft:plains")

        return result
    }

}

data class WolfVariantType(
    val wildTexture: String,
    val tameTexture: String,
    val angryTexture: String
)