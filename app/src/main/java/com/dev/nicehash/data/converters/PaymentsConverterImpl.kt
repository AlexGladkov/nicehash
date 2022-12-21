package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiPayment
import com.dev.nicehash.domain.models.Payout
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.time.format.DateTimeFormatter

/**
 * Created by Alex Gladkov on 05.08.18.
 * Implementation for payments converter
 */
class PaymentsConverterImpl: PaymentsConverter {

    override fun apiToModel(apiPayment: ApiPayment): Payout {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
        return Payout(dateTime = formatter.parseDateTime(apiPayment.time).toString("d/MM · HH:mm"),
                fee = ((apiPayment.fee / apiPayment.amount) * 100).toFloat(), payBtc = apiPayment.amount, feeBtc = apiPayment.fee)
    }
}