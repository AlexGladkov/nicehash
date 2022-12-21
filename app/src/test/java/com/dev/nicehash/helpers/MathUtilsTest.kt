package com.dev.nicehash.helpers

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MathUtilsTest {

    @Test
    fun averageIsCorrect() {
        val collection = listOf(6.0, 6.0, 6.0, 6.0)
        assertEquals(6f, com.dev.nicehash.helpers.MathUtils.getAverage(collection = collection).toFloat())
    }
}
