package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiExchangeRate
import com.dev.nicehash.data.room.models.ExchangeRateEntity
import com.dev.nicehash.domain.models.ExchangeRate

/**
 * Created by Alex Gladkov on 11.08.18.
 * Implementation for currency converter
 */
class ExchangeRateConverterImpl : ExchangeRateConverter {
    override fun apiToDB(apiExchange: ApiExchangeRate): ExchangeRateEntity {
        return ExchangeRateEntity(id = null, code = apiExchange.code, value = apiExchange.value.toDouble())
    }

    override fun apiToModel(apiExchange: ApiExchangeRate): ExchangeRate {
        return ExchangeRate(code = apiExchange.code, value = apiExchange.value.toDouble())
    }

    override fun dbToModel(currencyEntity: ExchangeRateEntity): ExchangeRate {
        return ExchangeRate(code = currencyEntity.code, value = currencyEntity.value)
    }

    override fun modelToDB(exchangeRate: ExchangeRate): ExchangeRateEntity {
        return ExchangeRateEntity(id = null, code = exchangeRate.code, value = exchangeRate.value)
    }
}