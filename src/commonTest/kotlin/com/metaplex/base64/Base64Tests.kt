package com.metaplex.base64

import kotlin.test.Test
import kotlin.test.assertEquals

class Base64Tests {

    @Test
    fun testBase64EncodeToString() {
        // given
        val rawString = "Kotlin is awesome"
        val expectedEncodedString = "S290bGluIGlzIGF3ZXNvbWU="

        // then
        val actualEncodedString = Base64Factory.createEncoder().encodeToString(rawString.encodeToByteArray())

        assertEquals(expectedEncodedString, actualEncodedString)
    }

    @Test
    fun testBase64EncodeEmptyString() {
        // given
        val rawString = ""
        val expectedEncodedString = ""

        // then
        val actualEncodedString = Base64Factory.createEncoder().encodeToString(rawString.encodeToByteArray())

        assertEquals(expectedEncodedString, actualEncodedString)
    }

    @Test
    fun testBase64EncodePaddedString() {
        // given
        val rawString = "22"
        val expectedEncodedString = "MjI="

        // then
        val actualEncodedString = Base64Factory.createEncoder().encodeToString(rawString.encodeToByteArray())

        assertEquals(expectedEncodedString, actualEncodedString)
    }

    @Test
    fun testBase64DecodeString() {
        // given
        val encodedString = "S290bGluIGlzIGF3ZXNvbWU="
        val expectedString = "Kotlin is awesome"

        // then
        val actualString = Base64Factory.createEncoder().decode(encodedString).decodeToString()

        assertEquals(expectedString, actualString)
    }

    @Test
    fun testBase64DecodeEmptyString() {
        // given
        val encodedString = ""
        val expectedString = ""

        // then
        val actualString = Base64Factory.createEncoder().decode(encodedString).decodeToString()

        assertEquals(expectedString, actualString)
    }

    @Test
    fun testBase64DecodePaddedString() {
        // given
        val encodedString = "MjI="
        val expectedString = "22"

        // then
        val actualString = Base64Factory.createEncoder().decode(encodedString).decodeToString()

        assertEquals(expectedString, actualString)
    }
}