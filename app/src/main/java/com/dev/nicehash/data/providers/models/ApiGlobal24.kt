package com.dev.nicehash.data.providers.models

/**
 * Created by Alex Gladkov on 11/10/2018.
 */
data class ApiGlobal24(val method: String, val result: List<ApiGlobal24Stats>)
data class ApiGlobal24Stats(val price: Double, val algo: Int, val speed: Double)