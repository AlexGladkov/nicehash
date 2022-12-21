package com.dev.nicehash.data.providers.models

/**
 * Created by Alex Gladkov on 11/10/2018.
 * Models for stats provider request
 */
data class ApiProvider(val method: String, val result: List<ApiProviderStats>)
data class ApiProviderStats(val balance: Double, val rejected_speed: Double,
                            val algo: Int, val accepted_speed: Double)