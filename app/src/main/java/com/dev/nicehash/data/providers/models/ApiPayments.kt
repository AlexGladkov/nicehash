package com.dev.nicehash.data.providers.models

/**
 * Created by neura on 11/10/2018.
 */
data class ApiPayments(val method: String, val payments: List<ApiPaymentsData>, val nh_wallet: Boolean,
                       val addr: String)
data class ApiPaymentsData(val amount: Double, val fee: Double,
                            val TXID: String, val time: Long, val type: Int)