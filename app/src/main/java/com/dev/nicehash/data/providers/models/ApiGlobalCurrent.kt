package com.dev.nicehash.data.providers.models

/**
 * Created by Alex Gladkov on 11/10/2018.
 * Models for global current api request
 */
data class ApiGlobalCurrent(val method: String, val result: List<ApiGlobalCurrentStats>)
data class ApiGlobalCurrentStats(val profitability_above_ltc: Double, val price: Double,
                                 val profitability_ltc: Double, val speed: Double)