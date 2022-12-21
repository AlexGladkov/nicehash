package com.dev.nicehash.data.providers.models

import com.google.gson.JsonObject

/**
 * Created by Alex Gladkov on 05.08.18.
 * General class for all api responses
 */
data class ApiResponse(val result: JsonObject, val method: String)