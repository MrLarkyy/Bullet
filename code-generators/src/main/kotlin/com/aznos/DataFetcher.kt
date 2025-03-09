package com.aznos

import com.google.gson.JsonObject

object DataFetcher {

    private val dataKeys: JsonObject = Main.gson.fromJson(Main.dataFolder.resolve("data/dataPaths.json").readText(), JsonObject::class.java).getAsJsonObject("pc").getAsJsonObject(
        Main.VERSION)
    private val cache = mutableMapOf<String, JsonObject>()

    fun getData(key: String): JsonObject {
        if (cache.containsKey(key)) return cache[key]!!
        if (!dataKeys.has(key)) throw IllegalArgumentException("Unknown key \"$key\"")

        val json = Main.gson.fromJson(Main.dataFolder.resolve("data/").resolve(dataKeys.get(key).asString).readText(), JsonObject::class.java)
        cache[key] = json
        return json
    }

}