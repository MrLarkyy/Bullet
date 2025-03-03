package com.aznos.events

/**
 * Base class for all events
 *
 * @property isCancelled Whether the event is cancelled
 */
open class Event {
    var isCancelled: Boolean = false
}