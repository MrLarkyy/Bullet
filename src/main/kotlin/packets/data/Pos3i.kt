package com.aznos.packets.data

class Pos3i(
    val x: Int,
    val y: Int,
    val z: Int
) {
}

fun Pos3i.toLong(): Long = (x.toLong() shl 42) or (y.toLong() shl 21) or z.toLong()
fun Long.toPos3i(): Pos3i {
    val x = (this shr 38).toInt()
    val y = (this shl 52 shr 52).toInt()
    val z = (this shl 26 shr 38).toInt()
    return Pos3i(x, y, z)
}