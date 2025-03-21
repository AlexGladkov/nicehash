package tech.mobiledeveloper.microbench

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleMacroBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.dev.nicehash", // Замени на пакет своего приложения
        metrics = listOf(StartupTimingMetric()), // Метрика измерения старта
        iterations = 10, // Количество итераций
        startupMode = StartupMode.COLD // "COLD" - запуск без кеша, "WARM" - повторный запуск
    ) {
        pressHome() // Выход на главный экран перед тестом
        startActivityAndWait() // Запуск приложения
    }
}