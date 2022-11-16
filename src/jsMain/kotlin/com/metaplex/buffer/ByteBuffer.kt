/*
 * JS Platform ByteBuffer Implementation
 * Metaplex
 *
 * Created by Funkatronics on 11/15/2022
 */

package com.metaplex.buffer

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.khronos.webgl.set

actual fun ByteBuffer.Companion.allocate(capacity: Int): ByteBuffer = JsByteBuffer(Uint8Array(capacity))
actual fun ByteBuffer.Companion.wrap(array: ByteArray): ByteBuffer = JsByteBuffer(Uint8Array(array.toTypedArray()))

class JsByteBuffer(val base: Uint8Array) : ByteBuffer {

    private var byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
    private var position: Int = 0

    override val capacity: Int get() = base.length
    override val order: ByteOrder get() = byteOrder

    override fun order(order: ByteOrder): ByteBuffer {
        byteOrder = order
        return this
    }

    override fun get(): Byte = base[position++]

    override fun put(byte: Byte): ByteBuffer = this.also { base[position++] = byte }

    override fun array(): ByteArray =
        ByteArray(base.length).apply {
            get(this)
        }
}