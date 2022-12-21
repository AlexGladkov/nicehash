package com.dev.nicehash.app.servers

/**
 * Created by Alex Gladkov on 13.08.18.
 * Server for get current exchange rates (updates in background)
 */
interface ExchangeServer {
    fun convertBtcFor(code: String, value: Double): Float
    fun fetchRate(code: String): Float
}