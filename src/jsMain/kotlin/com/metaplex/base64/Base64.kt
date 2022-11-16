/*
 * JS Platform Base64 Implementation
 * Metaplex
 *
 * Created by Funkatronics on 11/15/2022
 */

package com.metaplex.base64

import kotlinx.browser.window

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = JsBase64Encoder
}

object JsBase64Encoder : Base64Encoder {
    override fun decode(src: ByteArray): ByteArray {
        val string = src.decodeToString()
        val encodedString = window.atob(string)
        return encodedString.encodeToByteArray()
    }

    override fun encode(src: ByteArray): ByteArray {
        val string = src.decodeToString()
        val encodedString = window.btoa(string)
        return encodedString.encodeToByteArray()
    }
}