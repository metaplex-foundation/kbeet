/*
 * JS Platform Base64 Implementation
 * Metaplex
 *
 * Created by Funkatronics on 11/15/2022
 */

package com.metaplex.base64

import Buffer
import kotlinx.browser.window

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = JsBase64Encoder
}

object JsBase64Encoder : Base64Encoder {
    override fun decode(src: ByteArray): ByteArray {
        val string = src.decodeToString()
        val encodedString =
            if (jsTypeOf(window) == "undefined")
                Buffer.from(string, "base64").toString("utf-8")
            else window.atob(string)

        return encodedString.encodeToByteArray()
    }

    override fun encode(src: ByteArray): ByteArray {
        val string = src.decodeToString()
        val encodedString =
            if (jsTypeOf(window) == "undefined")
                Buffer.from(string, "utf-8").toString("base64")
            else window.btoa(string)

        return encodedString.encodeToByteArray()
    }
}