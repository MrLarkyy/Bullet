package com.aznos.entity.player.data.chat

import com.aznos.datatypes.CollectionType.readCollection
import com.aznos.datatypes.CollectionType.writeCollection
import com.aznos.datatypes.StringType.readString
import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.VarInt.readVarInt
import com.aznos.datatypes.VarInt.writeVarInt
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import java.io.DataInputStream
import java.io.DataOutputStream

data class ChatTypeDecoration(
    val translationKey: String,
    val parameters: Collection<Parameter>,
    val style: Style
) {

    fun decorate(component: Component, type: ChatType.Bound): Component {
        val components = Array<ComponentLike>(parameters.size) { Component.empty() }
        parameters.forEachIndexed { index, parameter ->
            components[index] = parameter.selector(component, type)
        }
        return Component.translatable(
            translationKey,
            null,
            style,
            *components
        )
    }

    fun write(os: DataOutputStream) {
        os.writeString(translationKey)
        os.writeCollection(parameters) { _, v ->
            os.writeVarInt(v.ordinal)
        }
        os.writeString(GsonComponentSerializer.gson().serializer().toJson(style))
    }

    enum class Parameter(val id: String, val selector: (Component, ChatType.Bound) -> Component) {
        SENDER("sender", { component, type -> type.name }),
        TARGET("target", { component, type -> type.target ?: Component.empty()}),
        CONTENT("content", { component, _ -> component });

        companion object {
            val map = entries.associateBy { it.id }
        }
    }

    companion object {
        fun withSender(translationKey: String): ChatTypeDecoration {
            return ChatTypeDecoration(translationKey, listOf(Parameter.SENDER, Parameter.CONTENT), Style.empty())
        }

        fun incomingDirectMessage(translationKey: String): ChatTypeDecoration {
            return ChatTypeDecoration(translationKey, listOf(Parameter.SENDER, Parameter.CONTENT), Style.style(NamedTextColor.GRAY,TextDecoration.ITALIC))
        }
        fun outgoingDirectMessage(translationKey: String): ChatTypeDecoration {
            return ChatTypeDecoration(translationKey, listOf(Parameter.TARGET, Parameter.CONTENT), Style.style(NamedTextColor.GRAY,TextDecoration.ITALIC))
        }
        fun teamMessage(translationKey: String): ChatTypeDecoration {
            return ChatTypeDecoration(translationKey, listOf(Parameter.TARGET,Parameter.SENDER, Parameter.CONTENT), Style.empty())
        }

        fun read(input: DataInputStream): ChatTypeDecoration {
            val translationKey = input.readString()
            val parameters = input.readCollection({ i -> ArrayList(i)}, { _ -> Parameter.entries[input.readVarInt()] })
            val style: Style = GsonComponentSerializer.gson().serializer().fromJson(input.readString(), Style::class.java)

            return ChatTypeDecoration(translationKey, parameters, style)
        }
    }

}