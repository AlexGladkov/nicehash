package com.dev.nicehash.data.providers.models

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

/**
 * Created by Alex Gladkov on 11/10/2018.
 */
data class ApiProviderEx(val method: String, val current: List<ApiProviderCurrent>, val past: List<ApiProviderPast>,
                       val payments: List<ApiPayment>, val addr: String)
data class ApiProviderCurrent(val profitability: Double, val suffix: String, val name: String,
                              val algo: Int, val data: ApiProviderData)
data class ApiProviderPast(val algo: Int, val data: List<ApiProviderPastData>)
data class ApiProviderData(val a: Float, val rs: Float, val unpaid: Double)
data class ApiProviderPastData(val time: Long, val a: Float, val unpaid: Double)