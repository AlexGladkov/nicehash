package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiExchangeRate
import com.dev.nicehash.data.room.models.ExchangeRateEntity
import com.dev.nicehash.domain.models.ExchangeRate

/**
 * Created by Alex Gladkov on 11.08.18.
 * Converter between api, db, domain for currency
 */
interface ExchangeRateConverter {
    fun dbToModel(currencyEntity: ExchangeRateEntity): ExchangeRate
    fun modelToDB(exchangeRate: ExchangeRate): ExchangeRateEntity
    fun apiToDB(apiExchange: ApiExchangeRate): ExchangeRateEntity
    fun apiToModel(apiExchange: ApiExchangeRate): ExchangeRate
}