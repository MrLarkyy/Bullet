package com.aznos.packets.data.chunk

import dev.dewy.nbt.tags.collection.CompoundTag

class ChunkColumn {
    val x: Int
    val z: Int
    val chunks: List<Chunk>
    val tileEntities: List<TileEntity>
    val hasHeightMaps: Boolean
    val heightMaps: CompoundTag
    val hasBiomeData: Boolean
    var biomeDataInts: IntArray? = null
    var biomeDataBytes: ByteArray? = null

    constructor(x: Int, y: Int, chunks: List<Chunk>, tileEntities: List<TileEntity>, biomeData: IntArray) {
        this.x = x
        this.z = y
        this.chunks = chunks
        this.tileEntities = tileEntities
        this.hasHeightMaps = false
        this.heightMaps = CompoundTag()
        this.hasBiomeData = true
        this.biomeDataInts = biomeData
    }

    constructor(x: Int, y: Int, chunks: List<Chunk>, tileEntities: List<TileEntity>) {
        this.x = x
        this.z = y
        this.chunks = chunks
        this.tileEntities = tileEntities
        this.hasHeightMaps = false
        this.heightMaps = CompoundTag()
        this.hasBiomeData = false
        this.biomeDataInts = IntArray(1024)
    }

    constructor(x: Int, y: Int, chunks: List<Chunk>, tileEntities: List<TileEntity>, heightMaps: CompoundTag) {
        this.x = x
        this.z = y
        this.chunks = chunks
        this.tileEntities = tileEntities
        this.hasHeightMaps = true
        this.heightMaps = heightMaps
        this.hasBiomeData = false
        this.biomeDataInts = IntArray(1024)
    }

    constructor(x: Int, y: Int, chunks: List<Chunk>, tileEntities: List<TileEntity>, heightMaps: CompoundTag, biomeDataInts: IntArray) {
        this.x = x
        this.z = y
        this.chunks = chunks
        this.tileEntities = tileEntities
        this.hasHeightMaps = true
        this.heightMaps = heightMaps
        this.hasBiomeData = true
        this.biomeDataInts = biomeDataInts
    }

    constructor(x: Int, y: Int, chunks: List<Chunk>, tileEntities: List<TileEntity>, heightMaps: CompoundTag, biomeDataBytes: ByteArray) {
        this.x = x
        this.z = y
        this.chunks = chunks
        this.tileEntities = tileEntities
        this.hasHeightMaps = true
        this.heightMaps = heightMaps
        this.hasBiomeData = true
        this.biomeDataBytes = biomeDataBytes
    }

    constructor(x: Int, y: Int, chunks: List<Chunk>, tileEntities: List<TileEntity>, biomeDataBytes: ByteArray) {
        this.x = x
        this.z = y
        this.chunks = chunks
        this.tileEntities = tileEntities
        this.hasHeightMaps = false
        this.heightMaps = CompoundTag()
        this.hasBiomeData = true
        this.biomeDataBytes = biomeDataBytes
    }
}