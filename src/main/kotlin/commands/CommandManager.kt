package com.aznos.commands

import com.aznos.entity.player.Player
import com.aznos.packets.data.GraphCommandNode
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.mojang.brigadier.tree.RootCommandNode
import com.sun.jdi.connect.Connector.Argument
import net.kyori.adventure.text.Component

object CommandManager {
    val dispatcher = CommandDispatcher<Player>()

    fun registerCommands() {
        dispatcher.register(
            LiteralArgumentBuilder.literal<Player>("test")
                .executes { context ->
                    context.source.clientSession.sendMessage(
                        Component.text("Hello world!")
                    )
                    1
                }
        )
    }

    fun buildCommandGraphFromDispatcher(dispatcher: CommandDispatcher<*>): Pair<List<GraphCommandNode>, Int> {
        val visited = mutableSetOf<CommandNode<*>>()
        val ordering = mutableListOf< CommandNode<*>>()

        fun traverse(node: CommandNode<*>) {
            if(visited.contains(node)) return
            visited.add(node)

            for(child in node.children) traverse(node)
            node.redirect?.let { traverse(it) }
            ordering.add(node)
        }

        traverse(dispatcher.root)
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
            val childrenIndices: List<Int> = node.children.mapNotNull { child ->
                indexMap[child]
            }

            val redirectIndex = node.redirect?.let { indexMap[it] }
            val name: String? = when(node) {
                is LiteralCommandNode<*> -> node.literal
                is ArgumentCommandNode<*, *> -> node.name
                else -> null
            }


            //TODO: Expand this
            val parser: String? = if(node is ArgumentCommandNode<*, *>) {
                "brigadier:string"
            } else null

            GraphCommandNode(
                flags = flags,
                children = childrenIndices,
                redirect = redirectIndex,
                name = name,
                parser = parser,
                properties = null,
                suggestionsType = null
            )
        }

        val rootIndex = indexMap[dispatcher.root] ?: 0
        return Pair(graphNodes, rootIndex)
    }
}