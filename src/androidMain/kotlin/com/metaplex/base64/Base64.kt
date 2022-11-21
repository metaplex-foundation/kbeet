package com.metaplex.base64

import android.util.Base64

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = AndroidBase64Encoder
}

object AndroidBase64Encoder : Base64Encoder {
    override fun decode(src: ByteArray): ByteArray = Base64.decode(src, Base64.DEFAULT)
    override fun encode(src: ByteArray): ByteArray = Base64.encode(src, Base64.DEFAULT)
    override fun decode(src: String): ByteArray = Base64.decode(src, Base64.DEFAULT)
    override fun encodeToString(src: ByteArray): String = Base64.encodeToString(src, Base64.DEFAULT)
}