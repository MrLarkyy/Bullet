package com.aznos.player

import com.google.gson.Gson

/**
 * Represents a chat message with various attributes such as text, translation key, color, and additional messages.
 *
 * @property text The main content of the chat message.
 * @property translate A key for translation purposes.
 * @property color The color of the message, represented by the [Color] enum.
 * @property extra A list of additional [ChatMessage] objects that can be appended to the main message.
 * @property with A list of [ChatMessage] objects used for translation purposes.
 */
class ChatMessage private constructor(
    val text: String?,
    val translate: String?,
    val color: Color?,
    val extra: List<ChatMessage>?,
    val with: List<ChatMessage>?
) {
    /**
     * Enum representing various color codes that can be applied to the chat message.
     *
     * @property code The character code representing the color.
     */
    enum class Color(val code: Char) {
        BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'),
        DARK_PURPLE('5'), GOLD('6'), GRAY('7'), DARK_GRAY('8'), BLUE('9'), GREEN('a'),
        AQUA('b'), RED('c'), LIGHT_PURPLE('d'), YELLOW('e'), WHITE('f');

        companion object {
            /**
             * Finds a color by its character code.
             *
             * @param c The character code.
             * @return The corresponding [Color] enum value, or null if not found.
             */
            fun fromChar(c: Char): Color? = entries.find { it.code == c }
        }
    }

    /**
     * Builder class for constructing [ChatMessage] objects.
     */
    class Builder {
        private var extra: MutableList<ChatMessage>? = null
        private var with: MutableList<ChatMessage>? = null
        private var text: String? = null
        private var translate: String? = null
        private var color: Color? = null

        /**
         * Builds and returns a [ChatMessage] object.
         *
         * @return The constructed [ChatMessage] object.
         */
        fun build(): ChatMessage {
            return ChatMessage(
                text,
                translate,
                color,
                extra?.toList(),
                with?.toList()
            )
        }

        /**
         * Sets the color of the chat message.
         *
         * @param color The color to set.
         * @return The [Builder] instance.
         */
        fun color(color: Color): Builder {
            this.color = color
            return this
        }

        /**
         * Adds an additional [ChatMessage] to the [extra] list.
         *
         * @param extra The additional [ChatMessage] to add.
         * @return The [Builder] instance.
         */
        fun extra(extra: ChatMessage): Builder {
            if (this.extra == null) this.extra = ArrayList()
            this.extra!!.add(extra)
            return this
        }

        /**
         * Sets the text of the chat message.
         *
         * @param text The text to set.
         * @return The [Builder] instance.
         */
        fun text(text: String): Builder {
            this.text = text
            return this
        }

        /**
         * Sets the translation key of the chat message.
         *
         * @param translate The translation key to set.
         * @return The [Builder] instance.
         */
        fun translate(translate: String): Builder {
            this.translate = translate
            return this
        }

        /**
         * Adds a [ChatMessage] to the [with] list for translation purposes.
         *
         * @param with The [ChatMessage] to add.
         * @return The [Builder] instance.
         */
        fun with(with: ChatMessage): Builder {
            if (this.with == null) this.with = ArrayList()
            this.with!!.add(with)
            return this
        }
    }

    /**
     * Converts the [ChatMessage] object to a JSON string.
     *
     * @return The JSON representation of the [ChatMessage].
     */
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        /**
         * Creates a [ChatMessage] with the given text, applying color codes.
         *
         * @param text The text of the message.
         * @return The constructed [ChatMessage] object.
         */
        fun text(text: String): ChatMessage {
            return Builder().text(applyColorCodes(text)).build()
        }

        /**
         * Creates a [ChatMessage] with a translation key and optional [with] messages.
         *
         * @param key The translation key.
         * @param with The [ChatMessage] objects for translation purposes.
         * @return The constructed [ChatMessage] object.
         */
        fun translate(key: String, vararg with: ChatMessage): ChatMessage {
            val builder = Builder().translate(key)
            with.forEach { builder.with(it) }
            return builder.build()
        }

        /**
         * Replaces color codes in the message with the appropriate characters.
         *
         * @param message The message with color codes.
         * @return The message with color codes replaced.
         */
        private fun applyColorCodes(message: String): String {
            val regex = "&([0-9a-f])".toRegex()
            return regex.replace(message) { match -> "§" + match.groupValues[1] }
        }
    }
}