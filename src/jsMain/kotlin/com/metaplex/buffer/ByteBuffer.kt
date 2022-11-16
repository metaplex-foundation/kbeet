/*
 * JS Platform ByteBuffer Implementation
 * Metaplex
 *
 * Created by Funkatronics on 11/15/2022
 */

package com.metaplex.buffer

import org.khronos.webgl.*

actual fun ByteBuffer.Companion.allocate(capacity: Int): ByteBuffer = JsByteBuffer(Uint8Array(capacity))
actual fun ByteBuffer.Companion.wrap(array: ByteArray): ByteBuffer = JsByteBuffer(Uint8Array(array.toTypedArray()))

class JsByteBuffer(val buffer: Uint8Array) : ByteBuffer {

    private var byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
    private var position: Int = 0

    override val capacity: Int get() = buffer.length
    override val order: ByteOrder get() = byteOrder

    override fun order(order: ByteOrder): ByteBuffer {
        byteOrder = order
        return this
    }

    override fun get(): Byte {
        val dataView = DataView(buffer.buffer, position++, 1)
        return dataView.getInt8(0)
    }

    override fun put(byte: Byte): ByteBuffer = this.also {
        val dataView = DataView(buffer.buffer, position++, 1)
        dataView.setInt8(0, byte)
    }

    override fun array(): ByteArray =
        Int8Array(buffer.buffer, 0, capacity).unsafeCast<ByteArray>()
}