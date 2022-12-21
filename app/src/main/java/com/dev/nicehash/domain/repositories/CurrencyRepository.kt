package com.dev.nicehash.domain.repositories

import com.dev.nicehash.domain.models.ExchangeRate
import io.reactivex.Single

/**
 * Created by Alex Gladkov on 11.08.18.
 * Repository for Currency API and DB
 */
interface CurrencyRepository {
    fun fetchExchangeRates(): Single<List<ExchangeRate>>
    fun fetchExchangeRatesFromDB(): Single<List<ExchangeRate>>
    fun updateExchangeRate(exchangeRate: ExchangeRate): Single<Boolean>
}