package com.aznos.packets.data.chunk.storage

abstract class BaseStorage {

    abstract val data: LongArray
    abstract val bitsPerEntry: Int

    abstract val size: Int

    abstract fun get(index: Int): Int

    abstract fun set(index: Int, value: Int)

}