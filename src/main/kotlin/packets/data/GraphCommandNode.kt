package com.aznos.packets.data

/**
 * Represents one command node in the command graph
 */
data class GraphCommandNode(
    val flags: Byte,
    val children: List<Int>,
    val redirect: Int? = null,
    val name: String? = null,
    val parser: String? = null,
    val properties: Any? = null,
    val suggestionsType: String? = null
)