package com.aznos.commands.data

/**
 * Enum class for the different types of strings that can be used in a command
 *
 * @property SINGLE A single word string
 * @property QUOTABLE A string that is in quotation marks
 * @property GREEDY A string that can be multiple words without quotations
 */
enum class StringTypes(id: Int) {
    SINGLE(0),
    QUOTABLE(1),
    GREEDY(2)
}