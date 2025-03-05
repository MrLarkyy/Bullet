package com.aznos.entity

open class Entity {
    val entityID: Int = lastID++

    companion object {
        private var lastID = 0
    }
}