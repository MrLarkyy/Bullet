package com.aznos.commands

import com.aznos.entity.player.Player
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
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
}