package com.aznos.datatypes

import com.aznos.datatypes.StringType.readString
import com.aznos.datatypes.StringType.writeString
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

object ComponentType {

    @Throws(IOException::class)
    fun DataInputStream.readComponent(): Component {
        val json = readString()
        return GsonComponentSerializer.gson().deserialize(json)
    }

    @Throws(IOException::class)
    fun DataOutputStream.writeComponent(component: Component) {
        val json = GsonComponentSerializer.gson().serialize(component)
        this.writeString(json)
    }
}