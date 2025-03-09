package util

import com.google.gson.JsonElement

object AssetFetcher {

    fun fetchJsonFile(path: String): JsonElement {
        return Main.gson.fromJson(Main.assetsFolder.resolve(path).readText(), JsonElement::class.java)
    }

    fun fetchChildrenJsonFiles(path: String): Map<String, JsonElement> {
        val folder = Main.assetsFolder.resolve(path)
        assert(folder.isDirectory)

        val result = mutableMapOf<String, JsonElement>()
        for (file in folder.listFiles()!!.filter { it.isFile && !it.startsWith("_") && it.endsWith(".json") }) {
            result[file.nameWithoutExtension] = Main.gson.fromJson(file.readText(), JsonElement::class.java)
        }

        return result
    }

}