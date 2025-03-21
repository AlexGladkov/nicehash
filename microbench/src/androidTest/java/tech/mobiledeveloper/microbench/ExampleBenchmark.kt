package tech.mobiledeveloper.microbench

import android.util.Log
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkStringConcatenation() {
        benchmarkRule.measureRepeated {
            val result = "Hello" + " " + "World"
            assert(result == "Hello World")
        }
    }

    @Test
    fun benchmarkListSorting() {
        lockScreen()
        val list = List(1000) { it }.shuffled()
        benchmarkRule.measureRepeated {
            list.sorted()
        }
    }

    private fun lockScreen() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.uiAutomation.executeShellCommand("input keyevent 26")
    }
}