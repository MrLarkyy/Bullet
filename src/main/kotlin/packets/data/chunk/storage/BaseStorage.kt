package com.aznos.packets.data.chunk.storage

abstract class BaseStorage {

    abstract fun getData(): LongArray
    abstract fun getBitsPerEntry(): Int

    abstract fun getSize(): Int

    abstract fun get(index: Int): Int

    abstract fun set(index: Int, value: Int)

}