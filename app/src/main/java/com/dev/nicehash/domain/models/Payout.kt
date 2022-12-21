package com.dev.nicehash.domain.models

/**
 * Created by Alex Gladkov on 15.07.18.
 * Model for payout tab (domain)
 */
data class Payout(val dateTime: String, val fee: Float, val payBtc: Double, val feeBtc: Double)