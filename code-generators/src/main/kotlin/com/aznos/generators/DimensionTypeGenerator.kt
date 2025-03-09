package com.aznos.generators

import com.aznos.util.AssetFetcher
import com.aznos.util.CodeGenerator
import com.palantir.javapoet.*
import org.jetbrains.annotations.NotNull
import javax.lang.model.element.Modifier

class DimensionTypeGenerator : CodeGenerator() {
    override fun generate() {
        val dimensionTypesEnum = TypeSpec.enumBuilder("DimensionTypes").addModifiers(Modifier.PUBLIC)

        dimensionTypesEnum.addFields(listOf(
            FieldSpec.builder(TypeName.FLOAT, "ambientLight", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "bedWorks", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(TypeName.DOUBLE, "coordinateScale", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(String::class.java, "effects", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "hasCeiling", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "hasRaids", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "hasSkyLight", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(TypeName.INT, "height", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(String::class.java, "infiniburn", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(TypeName.INT, "logicalHeight", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(TypeName.INT, "minY", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "natural", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "piglinSafe", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "respawnAnchorWorks", Modifier.PUBLIC, Modifier.FINAL).build(),
            FieldSpec.builder(Boolean::class.java, "ultrawarm", Modifier.PUBLIC, Modifier.FINAL).build(),
        ))

        dimensionTypesEnum.addMethod(
            MethodSpec.constructorBuilder()
            .addParameters(listOf(
                ParameterSpec.builder(TypeName.DOUBLE, "ambientLight").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "bedWorks").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(TypeName.DOUBLE, "coordinateScale").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(String::class.java, "effects").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "hasCeiling").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "hasRaids").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "hasSkyLight").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(TypeName.INT, "height").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(String::class.java, "infiniburn").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(TypeName.INT, "logicalHeight").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(TypeName.INT, "minY").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "natural").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "piglinSafe").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "respawnAnchorWorks").addAnnotation(NotNull::class.java).build(),
                ParameterSpec.builder(Boolean::class.java, "ultrawarm").addAnnotation(NotNull::class.java).build(),
            ))
            .addStatement("this.ambientLight = (float) ambientLight")
            .addStatement("this.bedWorks = bedWorks")
            .addStatement("this.coordinateScale = coordinateScale")
            .addStatement("this.effects = effects")
            .addStatement("this.hasCeiling = hasCeiling")
            .addStatement("this.hasRaids = hasRaids")
            .addStatement("this.hasSkyLight = hasSkyLight")
            .addStatement("this.height = height")
            .addStatement("this.infiniburn = infiniburn")
            .addStatement("this.logicalHeight = logicalHeight")
            .addStatement("this.minY = minY")
            .addStatement("this.natural = natural")
            .addStatement("this.piglinSafe = piglinSafe")
            .addStatement("this.respawnAnchorWorks = respawnAnchorWorks")
            .addStatement("this.ultrawarm = ultrawarm")
            .build())

        for (entry in AssetFetcher.fetchChildrenJsonFiles("data/minecraft/dimension_type")) {
            if (entry.key.contains("cave")) continue // exclude overworld_caves

            val json = entry.value.asJsonObject

            dimensionTypesEnum.addEnumConstant(entry.key.uppercase(), TypeSpec.anonymousClassBuilder(
                "\$L,\$L,\$L,\$S,\$L,\$L,\$L,\$L,\$S,\$L,\$L,\$L,\$L,\$L,\$L",
                json.get("ambient_light").asDouble,
                json.get("bed_works").asBoolean,
                json.get("coordinate_scale").asDouble,
                json.get("effects").asString,
                json.get("has_ceiling").asBoolean,
                json.get("has_raids").asBoolean,
                json.get("has_skylight").asBoolean,
                json.get("height").asInt,
                json.get("infiniburn").asString,
                json.get("logical_height").asInt,
                json.get("min_y").asInt,
                json.get("natural").asBoolean,
                json.get("piglin_safe").asBoolean,
                json.get("respawn_anchor_works").asBoolean,
                json.get("ultrawarm").asBoolean
            ).build())
        }

        writeClass(JavaFile.builder("com.aznos.data", dimensionTypesEnum.build()).build())
    }
}