package com.aznos.events

sealed class Events

data class PlayerJoinEvent(val username: String) : Event()