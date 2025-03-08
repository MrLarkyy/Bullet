package com.aznos.util.adventure

import dev.dewy.nbt.api.Tag
import dev.dewy.nbt.tags.collection.CompoundTag
import dev.dewy.nbt.tags.primitive.StringTag
import net.kyori.adventure.text.*
import net.kyori.adventure.text.event.DataComponentValue
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import java.util.*

object AdventureNBTSerializer {

    fun serialize(component: Component): Tag {
        if (component is TextComponent && !component.hasStyling() && component.children().isEmpty()) {
            return StringTag(component.content())
        }

        return serializeComponent(component)
    }

    fun serializeComponent(component: Component): CompoundTag {
        val writer = CompoundTag()

        if (component is TextComponent) {
            writer.putString("text", component.content())
        } else if (component is TranslatableComponent) {
            writer.putString("translate", component.key())
            component.fallback()?.let { fallback ->
                writer.putString("fallback", fallback)
            }

            val args = component.arguments()
            if (args.isNotEmpty()) {
                writer.putList("with", serializeTranslationArgumentList(args))
            }
        } else if (component is ScoreComponent) {
            val score = CompoundTag()
            val scoreName = component.name()
            score.putString("name", scoreName)

            val objective = component.objective()
            score.putString("objective", objective)
            writer.putCompound("score", score.value)
        } else if (component is SelectorComponent) {
            writer.putString("selector", component.pattern())
            component.separator()?.let { writer.put<CompoundTag>("separator", serializeComponent(it)) }
        } else if (component is KeybindComponent) {
            writer.putString("keybind", component.keybind())
        } else if (component is NBTComponent<*, *>) {
            val nbtPath = component.nbtPath()
            writer.putString("nbt", nbtPath)

            if (component.interpret()) {
                writer.putByte("interpret", 1)
            }

            component.separator()?.let {
                writer.put<CompoundTag>("separator", serialize(it))
            }
            when (component) {
                is BlockNBTComponent -> {
                    val pos = component.pos()
                    writer.putString("block", pos.asString())
                }

                is EntityNBTComponent -> {
                    val selector = component.selector()
                    writer.putString("entity", selector)
                }

                is StorageNBTComponent -> {
                    val storage = component.storage()
                    writer.putString("storage", storage.asString())
                }
            }
        }

        if (component.hasStyling()) {
            for (value in serializeStyle(component.style()).values()) {
                writer.values().add(value)
            }
        }

        val children = component.children()
        if (children.isNotEmpty()) {
            writer.putList("extra", children.map { serializeComponent(it) })
        }

        return writer
    }

    fun serializeStyle(style: Style): CompoundTag {
        if (style.isEmpty) {
            return CompoundTag()
        }

        val writer = CompoundTag()
        val font = style.font()
        if (font != null) {
            writer.putString("font", font.asString())
        }
        val color = style.color()
        if (color != null) {
            val serialized = serializeColor(color)
            if (serialized != null) {
                writer.putString("color", serialized)
            }
        }
        val shadowColor = style.shadowColor()
        if (shadowColor != null) {
            writer.putInt("shadow_color", shadowColor.value())
        }
        for (decoration in TextDecoration.NAMES.values()) {
            val state: TextDecoration.State = style.decoration(decoration)
            if (state != TextDecoration.State.NOT_SET) {
                writer.putByte(decoration.toString(), if (state == TextDecoration.State.TRUE) 1 else 0)
            }
        }

        val insertion = style.insertion()
        if (insertion != null) {
            writer.putString("insertion", insertion)
        }

        val clickEvent = style.clickEvent()
        if (clickEvent != null) {
            val child = CompoundTag()
            child.putString("action", clickEvent.action().toString())
            child.putString("value", clickEvent.value())
            writer.putCompound("clickEvent", child.value)
        }

        val hoverEvent = style.hoverEvent()
        if (hoverEvent != null) {
            val child = CompoundTag()
            when(hoverEvent.action().toString()) {
                "show_text" -> {
                    child.putAny("contents", this.serialize(hoverEvent.value() as Component))
                }
                "show_item" -> {
                    val item = hoverEvent.value() as HoverEvent.ShowItem
                    val itemId = item.item()
                    val count = item.count()
                    val nbt = item.nbt()
                    val emptyComps = item.dataComponents().isEmpty()

                    if (count == 1 && nbt == null && emptyComps) {
                        child.putString("contents", itemId.asString())
                    } else {
                        val itemNBT = CompoundTag()
                        itemNBT.putString("id", itemId.asString())
                        itemNBT.putInt("count", count)
                        if (nbt != null) {
                            itemNBT.putString("tag",nbt.string())
                        }
                        if (!emptyComps) {
                            val compsNbt = CompoundTag()
                            for ((key, value) in item.dataComponents().entries) {
                                if (value == DataComponentValue.removed()) {
                                    compsNbt.putCompound("!$key", HashMap())
                                    continue
                                }

                                if (value is NbtComponentValue) {
                                    val nbt = value.nbt
                                    compsNbt.putAny(key.toString(), nbt)
                                }
                            }
                            itemNBT.putCompound("components",compsNbt.value)
                        }

                        child.putCompound("contents",itemNBT.value)
                    }
                }
                "show_entity" -> {
                    val showEntity = hoverEvent.value() as HoverEvent.ShowEntity
                    val entity = CompoundTag()
                    entity.putString("type", showEntity.type().asString())
                    entity.putIntArray("id", showEntity.id().toIntArray())

                    child.putCompound("contents", entity.value)
                }
            }

            writer.putCompound("hoverEvent", child.value)
        }

        return writer
    }

    private fun serializeColor(color: TextColor): String? {
        if (color is NamedTextColor) {
            return NamedTextColor.NAMES.key(color)
        }
        return String.format(Locale.ROOT, "%c%06X", TextColor.HEX_CHARACTER, color.value())
    }

    private fun serializeTranslationArgumentList(value: List<TranslationArgument>): List<CompoundTag> {
        val arguments = mutableListOf<CompoundTag>()
        for (translationArgument in value) {
            arguments += serializeComponent(translationArgument.asComponent())
        }
        return arguments
    }

    fun deserialize() {

    }

    fun CompoundTag.putAny(name: String, value: Tag) {
        value.name = name
        this.value[name] = value
    }

    class NbtComponentValue(
        val nbt: Tag
    ) : DataComponentValue

    fun UUID.toIntArray(): IntArray {
        return intArrayOf(
            (mostSignificantBits shr 32).toInt(),
            mostSignificantBits.toInt(),
            (leastSignificantBits shr 32).toInt(),
            leastSignificantBits.toInt()
        )
    }

}