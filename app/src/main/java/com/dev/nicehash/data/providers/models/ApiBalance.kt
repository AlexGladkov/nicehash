package com.dev.nicehash.data.providers.models

import com.google.gson.JsonArray

/**
 * Created by Alex Gladkov on 11.08.18.
 * Data class for backend models
 */
data class ApiBalance(val profitability: Double, val data: JsonArray, val name: String,
                      val suffix: String, val algo: Int)