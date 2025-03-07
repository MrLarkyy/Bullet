package com.aznos.packets.newPacket

import java.util.*

data class ResourceLocation(
    val namespace: String,
    val key: String
) {

    companion object {
        const val VANILLA_NAMESPACE = "minecraft"

        fun fromString(string: String): ResourceLocation {
            val array = if (string.contains(':')) string.split(':') else listOf(VANILLA_NAMESPACE, string)
            return ResourceLocation(array[0], array[1])
        }

        fun vanilla(key: String): ResourceLocation = ResourceLocation(VANILLA_NAMESPACE, key)
    }

    override fun toString(): String = "$namespace:$key"
    override fun hashCode(): Int {
        return Objects.hash(namespace, key)
    }
}