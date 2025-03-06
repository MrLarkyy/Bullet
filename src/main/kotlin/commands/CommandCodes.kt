package com.aznos.commands

enum class CommandCodes(val id: Int) {
    UNKNOWN(0),
    SUCCESS(1),
    ILLEGAL_ARGUMENT(2),
    INVALID_PERMISSIONS(3)
}