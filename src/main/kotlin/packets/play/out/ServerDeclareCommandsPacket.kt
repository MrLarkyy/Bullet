package com.aznos.packets.play.out

import com.aznos.datatypes.StringType.writeString
import com.aznos.datatypes.VarInt.writeVarInt
import com.aznos.packets.Packet
import com.aznos.packets.data.GraphCommandNode
import java.io.IOException

class ServerDeclareCommandsPacket(
    private val nodes: List<GraphCommandNode>,
    private val rootIndex: Int
) : Packet(0x10) {
    init {
        wrapper.writeVarInt(nodes.size)
        for(node in nodes) {
            wrapper.writeByte(node.flags.toInt())
            wrapper.writeVarInt(node.children.size)

            for(child in node.children) {
                wrapper.writeVarInt(child)
            }

            if(node.flags.toInt() and 0x08 != 0) {
                if(node.redirect == null) {
                    throw IOException("Redirect flag set but no redirect node index provided")
                }

                wrapper.writeVarInt(node.redirect)
            }

            if(node.name != null) {
                wrapper.writeString(node.name)
            }

            if(node.parser != null) {
                wrapper.writeString(node.parser)
            }

            if(node.flags.toInt() and 0x10 != 0) {
                if(node.suggestionsType == null) {
                    throw IOException("Suggestions flag set but no suggestions type provided")
                }

                wrapper.writeString(node.suggestionsType)
            }

            wrapper.writeVarInt(rootIndex)
        }
    }
}