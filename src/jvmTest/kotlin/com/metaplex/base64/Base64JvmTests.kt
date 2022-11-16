package com.metaplex.base64

import kotlin.test.Test
import kotlin.test.assertEquals

class Base64JvmTests {

    @Test
    fun testBase64EncodeNonAsciiString() {
        // given
        val utf8String = "Gödel"
        val expectedBytes = "R8O2ZGVs"

        // then
        val actualBytes = Base64Factory.createEncoder().encodeToString(utf8String.encodeToByteArray())

        assertEquals(expectedBytes, actualBytes)
    }
}