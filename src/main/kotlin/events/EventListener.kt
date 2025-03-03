package com.aznos.events

/**
 * An event listener that listens for a specific event type
 *
 * @param T The type of event to listen for
 */
fun interface EventListener<T : Event> {
    fun onEvent(event: T)
}