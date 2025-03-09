package com.aznos.registry

import com.aznos.datatypes.NBTType.writeNbtCompound
import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.newPacket.ResourceLocation
import dev.dewy.nbt.tags.collection.CompoundTag
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

abstract class Registry<T>(val key: ResourceLocation) {
    var cachedNetworkPacket: ByteArray? = null; private set
    private val map = mutableMapOf<ResourceLocation, T>()
    private var locked: Boolean = false

    abstract fun asCompound(value: T): CompoundTag

    fun lock() {
        if (locked) return
        locked = true

        val buffer = ByteArrayOutputStream()
        val wrapper = DataOutputStream(buffer)

        wrapper.writeString(key.toString())
        wrapper.writeVarInt(map.size)

        for ((key, value) in map) {
            wrapper.writeString(key.toString())
            wrapper.writeBoolean(true)
            wrapper.writeNbtCompound(asCompound(value))
        }

        cachedNetworkPacket = buffer.toByteArray()
    }

    private fun checkLocked() {
        check(!locked) {
            "Cannot operate on a locked registry"
        }
    }

    fun register(key: ResourceLocation, value: T): T {
        checkLocked()
        map[key] = value
        return value
    }

    fun unregister(key: ResourceLocation): Boolean {
        checkLocked()
        return map.remove(key) != null
    }

    fun lookup(key: ResourceLocation): T? = map[key]
    fun getMap(): Map<ResourceLocation, T> = map.toMap()
    fun getCompoundMap(): Map<ResourceLocation, CompoundTag> = map.mapValues { (_, value) -> asCompound(value) }

}