package com.aznos.entity

open class Entity {
    val entityID: Int

    companion object {
        private var lastID = 0
    }

    init {
        entityID = lastID++
    }
}