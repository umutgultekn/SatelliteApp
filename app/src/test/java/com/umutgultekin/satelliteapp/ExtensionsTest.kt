package com.umutgultekin.satelliteapp

import com.umutgultekin.satelliteapp.extensions.orFalse
import com.umutgultekin.satelliteapp.extensions.orZero
import com.umutgultekin.satelliteapp.extensions.toDateFormat
import com.umutgultekin.satelliteapp.extensions.toFormattedString
import kotlin.test.Test
import kotlin.test.assertEquals


class ExtensionsTest {

    @Test
    fun `orFalse should return false for null`() {
        val result: Boolean? = null
        assertEquals(false, result.orFalse())
    }

    @Test
    fun `orFalse should return true for true`() {
        val result = true
        assertEquals(true, result.orFalse())
    }

    @Test
    fun `orZero should return 0 for null Double`() {
        val result: Double? = null
        assertEquals(0.0, result.orZero(), 0.0)
    }

    @Test
    fun `toFormattedString should format Long value correctly`() {
        val value = 1234567L
        val formattedString = value.toFormattedString()
        assertEquals("1.234.567", formattedString)
    }

    @Test
    fun `toDateFormat should convert date correctly`() {
        val originalDate = "2024-10-20"
        val formattedDate = originalDate.toDateFormat()
        assertEquals("20.10.2024", formattedDate)
    }

    @Test
    fun `toDateFormat should return null for invalid date`() {
        val originalDate = "invalid-date"
        val formattedDate = originalDate.toDateFormat()
        assertEquals(null, formattedDate)
    }
}