package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiPayment
import com.dev.nicehash.domain.models.Payout

/**
 * Created by Alex Gladkov on 05.08.18.
 * Converter for payments between db, backend and app layer
 */
interface PaymentsConverter {
    fun apiToModel(apiPayment: ApiPayment): Payout
}