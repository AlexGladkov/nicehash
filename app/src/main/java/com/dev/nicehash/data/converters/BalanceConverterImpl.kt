package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiBalance
import com.dev.nicehash.domain.models.Balance

/**
 * Created by Alex Gladkov on 11.08.18.
 * Implementation for Balance Converter
 */
class BalanceConverterImpl: BalanceConverter {

    override fun apiToModel(apiBalance: ApiBalance): Balance {
        return Balance(id = 0, incomeValue = 0f, title = apiBalance.name, incomeBtc = apiBalance.profitability, value = 0f,
                btc = apiBalance.data[1].asDouble, suffix = apiBalance.suffix)
    }
}