package com.aznos.packets

import java.io.DataOutput
import java.io.IOException

abstract class ServerPacket(id: Int): Packet(id), DataOutput {

    //Data output specific functions
    @Throws(IOException::class)
    override fun write(b: ByteArray) {
        wrapper.write(b)
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        wrapper.write(b, off, len)
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        wrapper.write(b)
    }

    @Throws(IOException::class)
    override fun writeBoolean(v: Boolean) {
        wrapper.writeBoolean(v)
    }

    @Throws(IOException::class)
    override fun writeByte(v: Int) {
        wrapper.writeByte(v)
    }

    @Throws(IOException::class)
    override fun writeBytes(s: String) {
        wrapper.writeBytes(s)
    }

    @Throws(IOException::class)
    override fun writeChar(v: Int) {
        wrapper.writeChar(v)
    }

    @Throws(IOException::class)
    override fun writeChars(s: String) {
        wrapper.writeChars(s)
    }

    @Throws(IOException::class)
    override fun writeDouble(v: Double) {
        wrapper.writeDouble(v)
    }

    @Throws(IOException::class)
    override fun writeFloat(v: Float) {
        wrapper.writeFloat(v)
    }

    @Throws(IOException::class)
    override fun writeInt(v: Int) {
        wrapper.writeInt(v)
    }

    @Throws(IOException::class)
    override fun writeLong(v: Long) {
        wrapper.writeLong(v)
    }

    @Throws(IOException::class)
    override fun writeShort(v: Int) {
        wrapper.writeShort(v)
    }

    @Throws(IOException::class)
    override fun writeUTF(s: String) {
        wrapper.writeUTF(s)
    }

}