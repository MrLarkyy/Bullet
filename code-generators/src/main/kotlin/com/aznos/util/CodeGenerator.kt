package com.aznos.util

import com.aznos.Main
import com.palantir.javapoet.JavaFile

abstract class CodeGenerator {

    abstract fun generate()

    fun writeClass(file: JavaFile) = file.writeTo(Main.outputFolder)

}