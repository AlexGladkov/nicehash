package com.dev.nicehash.data.providers.models

/**
 * Created by Alex Gladkov on 05.08.18.
 * Data model class for backend payments
 */
data class ApiPayment(val amount: Double, val fee: Double, val time: String, val type: Int,
                      val TXID: String)