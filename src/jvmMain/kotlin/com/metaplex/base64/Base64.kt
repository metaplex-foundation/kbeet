package com.metaplex.base64

import java.util.*

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = JvmBase64Encoder
}

object JvmBase64Encoder : Base64Encoder {
    override fun decode(src: ByteArray): ByteArray = Base64.getDecoder().decode(src)
    override fun decode(src: String): ByteArray = Base64.getDecoder().decode(src)
    override fun encode(src: ByteArray): ByteArray = Base64.getEncoder().encode(src)
    override fun encodeToString(src: ByteArray): String = Base64.getEncoder().encodeToString(src)
}