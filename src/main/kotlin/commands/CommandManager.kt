package com.aznos.commands

import com.aznos.Bullet
import com.aznos.commands.data.DoubleProperties
import com.aznos.commands.data.IntegerProperties
import com.aznos.commands.data.StringTypes
import com.aznos.datatypes.StringType
import com.aznos.entity.player.Player
import com.aznos.packets.data.GraphCommandNode
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.mojang.brigadier.tree.RootCommandNode
import net.kyori.adventure.text.Component

object CommandManager {
    val dispatcher = CommandDispatcher<Player>()

    fun registerCommands() {
        dispatcher.register(
            LiteralArgumentBuilder.literal<Player>("say")
                .then(
                    RequiredArgumentBuilder.argument<Player, String>("message", StringArgumentType.greedyString())
                        .executes{ context ->
                            val message = StringArgumentType.getString(context, "message")
                            context.source.clientSession.sendMessage(
                                Component.text(message)
                            )
                            1
                        }
                )
        )
    }

    fun buildCommandGraphFromDispatcher(dispatcher: CommandDispatcher<*>): Pair<List<GraphCommandNode>, Int> {
        val visited = mutableSetOf<CommandNode<*>>()
        val ordering = mutableListOf<CommandNode<*>>()

        traverseCommandNodes(dispatcher.root, visited, ordering)

        val indexMap = ordering.withIndex().associate {
            it.value to it.index
        }

        val graphNodes = ordering.map { node ->
            val typeBits = when(node) {
                is RootCommandNode<*> -> 0
                is LiteralCommandNode<*> -> 1
                is ArgumentCommandNode<*, *> -> 2
                else -> 0
            }

            var flagsInt = typeBits
            if(node.command != null) flagsInt = flagsInt or 0x04
            if(node.redirect != null) flagsInt = flagsInt or 0x08

            val flags: Byte = flagsInt.toByte()
            val childrenIndices: List<Int> = node.children.mapNotNull { child -> indexMap[child] }
            val redirectIndex = node.redirect?.let { indexMap[it] }

            val name: String? = when (node) {
                is LiteralCommandNode<*> -> node.literal
                is ArgumentCommandNode<*, *> -> node.name
                else -> null
            }

            val(parser, propertiesValue) = if (node is ArgumentCommandNode<*, *>) {
                getParserAndProperties(node)
            } else {
                null to null
            }

            GraphCommandNode(
                flags = flags,
                children = childrenIndices,
                redirect = redirectIndex,
                name = name,
                parser = parser,
                properties = propertiesValue,
                suggestionsType = null
            )
        }

        val rootIndex = indexMap[dispatcher.root] ?: 0
        return Pair(graphNodes, rootIndex)
    }

    private fun traverseCommandNodes(
        node: CommandNode<*>,
        visited: MutableSet<CommandNode<*>>,
        ordering: MutableList<CommandNode<*>>
    ) {
        if(visited.contains(node)) return
        visited.add(node)

        for(child in node.children) {
            traverseCommandNodes(child, visited, ordering)
        }

        node.redirect?.let { traverseCommandNodes(it, visited, ordering) }
        ordering.add(node)
    }

    private fun getParserAndProperties(node: ArgumentCommandNode<*, *>): Pair<String?, Any?> {
        return when(node.type) {
            is StringArgumentType ->
                "brigadier:string" to StringTypes.GREEDY
            is IntegerArgumentType -> {
                val (min, max) = handleNumberArgumentType(
                    node.type as IntegerArgumentType,
                    "min",
                    "max",
                    -Int.MAX_VALUE,
                    Int.MAX_VALUE
                )

                var propFlags = 0
                if(min != Int.MIN_VALUE) propFlags = propFlags or 0x01
                if(max != Int.MAX_VALUE) propFlags = propFlags or 0x02

                "brigadier:integer" to IntegerProperties(
                    propFlags.toByte(),
                    if(propFlags and 0x01 != 0) min.toInt() else null,
                    if(propFlags and 0x02 != 0) max.toInt() else null
                )
            }
            is DoubleArgumentType -> {
                val (min, max) = handleNumberArgumentType(
                    node.type as DoubleArgumentType,
                    "min",
                    "max",
                    -Double.MAX_VALUE,
                    Double.MAX_VALUE
                )

                var propFlags = 0
                if(min != -Double.MAX_VALUE) propFlags = propFlags or 0x01
                if(max != Double.MAX_VALUE) propFlags = propFlags or 0x02

                "brigadier:double" to DoubleProperties(
                    propFlags.toByte(),
                    if(propFlags and 0x01 != 0) min.toDouble() else null,
                    if(propFlags and 0x02 != 0) max.toDouble() else null
                )
            }
            is BoolArgumentType -> {
                "brigadier:bool" to null
            }
            else ->
                "brigadier:string" to 2
        }
    }

    private fun <T> handleNumberArgumentType(
        type: T,
        minFieldName: String,
        maxFieldName: String,
        minDefault: Number,
        maxDefault: Number
    ): Pair<Number, Number> {
        val min = try {
            val field = type!!::class.java.getDeclaredField(minFieldName)
            field.isAccessible = true
            field.get(type) as Number
        } catch(e: NoSuchFieldException) {
            minDefault
        }

        val max = try {
            val field = type!!::class.java.getDeclaredField(maxFieldName)
            field.isAccessible = true
            field.get(type) as Number
        } catch(e: NoSuchFieldException) {
            maxDefault
        }

        return min to max
    }
}