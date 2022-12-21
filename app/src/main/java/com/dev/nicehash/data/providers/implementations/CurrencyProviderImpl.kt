package com.dev.nicehash.data.providers.implementations

import android.util.Log
import com.dev.nicehash.data.providers.CurrencyProvider
import com.dev.nicehash.data.providers.models.ApiExchangeRate
import com.dev.nicehash.data.providers.services.RemoteCurrencyService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Alex Gladkov on 11.08.18.
 * Implementation for currency provider
 */
class CurrencyProviderImpl(val remoteCurrencyService: RemoteCurrencyService): CurrencyProvider {
    private val TAG = CurrencyProviderImpl::class.java.simpleName

    override fun fetchExchangeRates(): Single<List<ApiExchangeRate>> {
        return remoteCurrencyService.fetchExchangeRate()
                .flatMap {
                    val converted: MutableList<ApiExchangeRate> = LinkedList()
                    it.keySet().forEach({ key ->
                        converted.add(ApiExchangeRate(code = key, value = it.get(key).asJsonObject.get("15m").asFloat,
                                symbol = it.get(key).asJsonObject.get("symbol").asString))
                    })

                    Single.just(converted)
                }
    }
}