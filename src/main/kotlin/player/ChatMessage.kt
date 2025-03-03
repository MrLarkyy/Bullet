package com.aznos.player

import com.google.gson.Gson

class ChatMessage private constructor(
    val text: String?,
    val translate: String?,
    val color: Color?,
    val extra: List<ChatMessage>?,
    val with: List<ChatMessage>?
) {
    enum class Color(val code: Char) {
        BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'),
        DARK_PURPLE('5'), GOLD('6'), GRAY('7'), DARK_GRAY('8'), BLUE('9'), GREEN('a'),
        AQUA('b'), RED('c'), LIGHT_PURPLE('d'), YELLOW('e'), WHITE('f');

        companion object {
            fun fromChar(c: Char): Color? = entries.find { it.code == c }
        }
    }

    class Builder {
        private var extra: MutableList<ChatMessage>? = null
        private var with: MutableList<ChatMessage>? = null
        private var text: String? = null
        private var translate: String? = null
        private var color: Color? = null

        fun build(): ChatMessage {
            return ChatMessage(
                text,
                translate,
                color,
                extra?.toList(),
                with?.toList()
            )
        }

        fun color(color: Color): Builder {
            this.color = color
            return this
        }

        fun extra(extra: ChatMessage): Builder {
            if (this.extra == null) this.extra = ArrayList()
            this.extra!!.add(extra)
            return this
        }

        fun text(text: String): Builder {
            this.text = text
            return this
        }

        fun translate(translate: String): Builder {
            this.translate = translate
            return this
        }

        fun with(with: ChatMessage): Builder {
            if (this.with == null) this.with = ArrayList()
            this.with!!.add(with)
            return this
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun text(text: String): ChatMessage {
            return Builder().text(applyColorCodes(text)).build()
        }

        fun translate(key: String, vararg with: ChatMessage): ChatMessage {
            val builder = Builder().translate(key)
            with.forEach { builder.with(it) }
            return builder.build()
        }

        private fun applyColorCodes(message: String): String {
            val regex = "&([0-9a-f])".toRegex()
            return regex.replace(message) { match -> "§" + match.groupValues[1] }
        }
    }
}