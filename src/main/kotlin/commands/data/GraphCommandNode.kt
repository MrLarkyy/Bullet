package com.aznos.commands.data

/**
 * Represents one command node in the command graph
 *
 * @param flags The flags of the command node, the available flags are
 * 0x03 - Node type (0: root, 1: literal, 2: argument)
 * 0x04 - Is executable - Set if the node stack to this point is a valid command
 * 0x08 - Has redirect - Set if the node redirects to another node
 * 0x10 - Has suggestions type - Only present for argument nodes
 *
 * @param children The children of the command node
 * @param redirect The redirect of the command node, only if flags has 0x08
 * @param name The name of the command node, only for argument and literal nodes
 * @param parser An optional parser for argument nodes so that client knows how to parse the argument
 * @param properties An optional property value for argument nodes
 * @param suggestionsType An optional suggestions type if flags contains 0x10
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