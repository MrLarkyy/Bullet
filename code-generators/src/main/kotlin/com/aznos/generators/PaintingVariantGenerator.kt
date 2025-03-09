package com.aznos.generators

import com.aznos.Main
import com.aznos.util.AssetFetcher
import com.aznos.util.CodeGenerator
import com.palantir.javapoet.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Modifier

class PaintingVariantGenerator : CodeGenerator() {
    override fun generate() {
        val enum = TypeSpec.enumBuilder("PaintingVariants").addModifiers(Modifier.PUBLIC)

        enum.addFields(listOf(
            FieldSpec.builder(String::class.java, "assetId", Modifier.PUBLIC, Modifier.FINAL).addAnnotation(NotNull::class.java).build(),
            FieldSpec.builder(Int::class.java, "height", Modifier.PUBLIC, Modifier.FINAL).addAnnotation(NotNull::class.java).build(),
            FieldSpec.builder(Int::class.java, "width", Modifier.PUBLIC, Modifier.FINAL).addAnnotation(NotNull::class.java).build(),
            FieldSpec.builder(String::class.java, "title", Modifier.PUBLIC, Modifier.FINAL).addAnnotation(Nullable::class.java).build(),
            FieldSpec.builder(String::class.java, "author", Modifier.PUBLIC, Modifier.FINAL).addAnnotation(Nullable::class.java).build()
        ))

        enum.addMethod(
            MethodSpec.constructorBuilder()
                .addParameters(listOf(
                    ParameterSpec.builder(String::class.java, "assetId").addAnnotation(NotNull::class.java).build(),
                    ParameterSpec.builder(Int::class.java, "height").addAnnotation(NotNull::class.java).build(),
                    ParameterSpec.builder(Int::class.java, "width").addAnnotation(NotNull::class.java).build(),
                    ParameterSpec.builder(String::class.java, "title").addAnnotation(Nullable::class.java).build(),
                    ParameterSpec.builder(String::class.java, "author").addAnnotation(Nullable::class.java).build()
                ))
                .addStatement("this.assetId = assetId")
                .addStatement("this.height = height")
                .addStatement("this.width = width")
                .addStatement("this.title = title")
                .addStatement("this.author = author")
                .build())

        for (entry in AssetFetcher.fetchChildrenJsonFiles("data/minecraft/painting_variant")) {
            val json = entry.value.asJsonObject

            enum.addEnumConstant(entry.key.uppercase(), TypeSpec.anonymousClassBuilder(
                "\$S,\$L,\$L,\$S,\$S",
                json.get("asset_id").asString,
                json.get("height").asInt,
                json.get("width").asInt,
                json.get("title")?.let { if (it.isJsonPrimitive) it.asString else Main.gson.toJson(it.asJsonObject) },
                json.get("author")?.let { if (it.isJsonPrimitive) it.asString else Main.gson.toJson(it.asJsonObject) },
            ).build())
        }

        writeClass(JavaFile.builder("com.aznos.data", enum.build()).build())
    }
}