package com.aznos.player

import com.google.gson.Gson

class ChatMessage private constructor(
    val text: String?,
    val translate: String?,
    var color: String?,
    val extra: Array<ChatMessage>?,
    val with: Array<ChatMessage>?
) {
    class Builder {
        private var extra: MutableList<ChatMessage>? = null
        private var with: MutableList<ChatMessage>? = null
        private var text: String? = null
        private var translate: String? = null
        private var color: String? = null

        fun build(): ChatMessage {
            return ChatMessage(
                text,
                translate,
                color,
                if(extra == null) null else extra!!.toTypedArray<ChatMessage>(),
                if(with == null) null else with!!.toTypedArray<ChatMessage>()
            )
        }

        fun color(color: String?): Builder {
            this.color = color
            return this
        }

        fun extra(extra: ChatMessage): Builder {
            if(this.extra == null) this.extra = ArrayList()
            this.extra!!.add(extra)
            return this
        }

        fun text(text: String?): Builder {
            this.text = text
            return this
        }

        fun translate(translate: String?): Builder {
            this.translate = translate
            return this
        }

        fun with(with: ChatMessage): Builder {
            if(this.with == null) this.with = ArrayList()
            this.with!!.add(with)
            return this
        }
    }

    fun setColor(color: String?): ChatMessage {
        this.color = color
        return this
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun text(text: String?): ChatMessage {
            return Builder().text(text).build()
        }

        fun translate(key: String?, vararg with: ChatMessage): ChatMessage {
            val bd = Builder().translate(key)
            for(element in with) bd.with(element)
            return bd.build()
        }
    }
}