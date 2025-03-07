package com.aznos.datatypes

import com.aznos.util.adventure.AdventureNBTSerializer
import net.kyori.adventure.text.Component
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.time.Instant

object ComponentType {

    @Throws(IOException::class)
    fun DataInputStream.readComponent(): Component {
        // TODO
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeComponent(component: Component) {
        val nbt = AdventureNBTSerializer.serialize(component)

    }
}