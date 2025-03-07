package com.aznos.packets.newPacket

import java.util.concurrent.ConcurrentHashMap

class KeyedRegistry<T,U>(
    map: MutableMap<KeyedEntryKey<T>, U> = ConcurrentHashMap(),
) {

    @Volatile
    var lastIndex: Int = -1
        private set

    private val registry = map
    private val keyToKeyed = ConcurrentHashMap<T, Keyed<T>>()
    private val indexToKey = ConcurrentHashMap<Int, Keyed<T>>()

    fun register(index: Int,key: T, value: U) {
        val keyed = KeyedEntryKey(index, key)
        indexToKey[keyed.id] = keyed
        keyToKeyed[keyed.value] = keyed
        registry[keyed] = value
        if (lastIndex < keyed.id) lastIndex = keyed.id
    }

    fun register(key: T, value: U) {
        register(lastIndex + 1, key, value)
    }
    operator fun get(key: T): U? {
        val keyed = keyToKeyed[key] ?: return null
        return registry[keyed]
    }
    operator fun get(keyed: Keyed<T>): U? = registry[keyed]
    operator fun get(index: Int): U? = registry[indexToKey[index]]

    fun keyBy(id: Int): Keyed<T>? = indexToKey[id]
    fun keyBy(key: T): Keyed<T>? = keyToKeyed[key]

    class KeyedEntryKey<T> internal constructor(index: Int, key: T): Keyed<T>(index, key)

}