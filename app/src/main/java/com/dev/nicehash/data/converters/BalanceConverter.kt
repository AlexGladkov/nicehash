package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiBalance
import com.dev.nicehash.domain.models.Balance

/**
 * Created by Alex Gladkov on 11.08.18.
 * Converter between api, db and domain for balance
 */
interface BalanceConverter {
    fun apiToModel(apiBalance: ApiBalance): Balance
}