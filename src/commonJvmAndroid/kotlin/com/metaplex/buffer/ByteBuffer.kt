package com.metaplex.buffer

import java.nio.ByteBuffer as Buffer

actual fun ByteBuffer.Companion.allocate(capacity: Int): ByteBuffer = JvmByteBuffer(Buffer.allocate(capacity))
actual fun ByteBuffer.Companion.wrap(array: ByteArray): ByteBuffer = JvmByteBuffer(Buffer.wrap(array))

class JvmByteBuffer(val buffer: Buffer) : ByteBuffer {

    override val capacity: Int get() = buffer.capacity()
    override val order: ByteOrder get() =
        if (buffer.order() == java.nio.ByteOrder.LITTLE_ENDIAN) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN

    override fun order(order: ByteOrder): ByteBuffer {
        buffer.order(when (order) {
            ByteOrder.BIG_ENDIAN -> java.nio.ByteOrder.BIG_ENDIAN
            ByteOrder.LITTLE_ENDIAN -> java.nio.ByteOrder.LITTLE_ENDIAN
        })

        return this
    }

    override fun get(): Byte = buffer.get()
    override fun getShort(): Short = buffer.short
    override fun getInt(): Int = buffer.int
    override fun getLong(): Long = buffer.long
    override fun getFloat(): Float = buffer.float
    override fun getDouble(): Double = buffer.double

    override fun put(byte: Byte): ByteBuffer = this.also { buffer.put(byte) }
    override fun putShort(value: Short): ByteBuffer = this.also { buffer.putShort(value) }
    override fun putInt(value: Int): ByteBuffer = this.also { buffer.putInt(value) }
    override fun putLong(value: Long): ByteBuffer = this.also { buffer.putLong(value) }
    override fun putFloat(value: Float): ByteBuffer = this.also { buffer.putFloat(value) }
    override fun putDouble(value: Double): ByteBuffer = this.also { buffer.putDouble(value) }

    override fun array(): ByteArray =
        if (buffer.hasArray()) buffer.array()
        else ByteArray(buffer.capacity()).apply {
            get(this)
        }
}