package com.aznos.generators

import com.aznos.util.AssetFetcher
import com.aznos.util.CodeGenerator
import com.palantir.javapoet.*
import org.jetbrains.annotations.NotNull
import javax.lang.model.element.Modifier

class WolfVariantGenerator : CodeGenerator() {
    override fun generate() {
        val enum = TypeSpec.enumBuilder("WolfVariants").addModifiers(Modifier.PUBLIC)

        enum.addFields(listOf(
            FieldSpec.builder(String::class.java, "wildTexture", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(String::class.java, "tameTexture", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(String::class.java, "angryTexture", Modifier.PUBLIC, Modifier.FINAL).build()
        ))
        enum.addMethod(
            MethodSpec.constructorBuilder()
            .addParameters(listOf(
                ParameterSpec.builder(String::class.java, "wildTexture").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(String::class.java, "tameTexture").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(String::class.java, "angryTexture").addAnnotation(NotNull::class.java).build()
            ))
            .addStatement("this.wildTexture = wildTexture")
            .addStatement("this.tameTexture = tameTexture")
            .addStatement("this.angryTexture = angryTexture")
            .build())

        for (entry in AssetFetcher.fetchChildrenJsonFiles("data/minecraft/wolf_variant")) {
            val json = entry.value.asJsonObject

            enum.addEnumConstant(entry.key.uppercase(), TypeSpec.anonymousClassBuilder(
                "\$S,\$S,\$S",
                json.get("wild_texture").asString,
                json.get("tame_texture").asString,
                json.get("angry_texture").asString,
            ).build())
        }

        writeClass(JavaFile.builder("com.aznos.data", enum.build()).build())
    }
}