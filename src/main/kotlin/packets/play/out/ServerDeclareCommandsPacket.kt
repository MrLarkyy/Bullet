package com.aznos.packets.play.out

import com.aznos.datatypes.UUIDType.writeUUID
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode

/**
 * This packet is sent to the client when it first joins, declaring all the commands that the server has
 *
 * @param nodes A list of all command nodes in the brigadier command graph
 * @param rootIndex The index of the root node in the command list
 */
class ServerDeclareCommandsPacket(
    nodes: List<CommandNode<*>>,
    rootIndex: Int
) : Packet(0x10) {
    init {
        wrapper.writeVarInt(nodes.size)

        val nodeIndexMap = nodes.withIndex().associate {
            it.value to it.index
        }

        nodes.forEach { node ->
            writeCommandNode(node, nodeIndexMap)
        }

        wrapper.writeVarInt(rootIndex)
    }

    /**
     * Serializes a brigadier command node into the packet format
     */
    private fun writeCommandNode(node: CommandNode<*>, nodeIndexMap: Map<CommandNode<*>, Int>) {
        var flags = 0
        when(node) {
            is LiteralCommandNode -> flags = 0x01
            is ArgumentCommandNode<*, *> -> flags = 0x02
        }

        if(node.redirect != null) flags = flags or 0x08 //Redirect flag
        if(node is ArgumentCommandNode<*, *>) flags = flags or 0x10 //Has suggestions

        wrapper.writeByte(flags)

        wrapper.writeVarInt(node.children.size)
        node.children.forEach { child ->
            wrapper.writeVarInt(nodeIndexMap[child] ?: -1)
        }

        node.redirect?.let { redirectNode ->
            wrapper.writeVarInt(nodeIndexMap[redirectNode] ?: -1)
        }

        when(node) {
            is LiteralCommandNode -> wrapper.writeUTF(node.literal)
            is ArgumentCommandNode<*, *> -> wrapper.writeUTF(node.name)
        }

        if(node is ArgumentCommandNode<*, *>) {
            writeArgumentType(node.type)
        }
    }

    /**
     * Writes the argument type of command node
     */
    private fun writeArgumentType(argumentType: ArgumentType<*>) {
        when(argumentType) {
            is IntegerArgumentType -> {
                wrapper.writeUTF("brigadier:integer")
            }
            is StringArgumentType -> {
                wrapper.writeUTF("brigadier:string")
            }
            else -> {
                wrapper.writeUTF("brigadier:unknown")
            }
        }
    }
}