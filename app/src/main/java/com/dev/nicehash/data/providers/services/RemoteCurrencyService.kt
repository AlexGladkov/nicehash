package com.dev.nicehash.data.providers.services

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Alex Gladkov on 11.08.18.
 * Api interface for currency
 */
interface RemoteCurrencyService {

    @GET("./ticker")
    fun fetchExchangeRate(): Single<JsonObject>
}