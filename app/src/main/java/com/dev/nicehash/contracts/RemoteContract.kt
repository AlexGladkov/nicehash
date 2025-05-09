package com.dev.nicehash.contracts

/**
 * Created by Alex Gladkov on 21.06.18.
 * Contract values for remote operations (endpoints, params, etc)
 */
class RemoteContract {

    companion object {
        val baseUrl = "https://api2.nicehash.com/main/api/v2/"

        const val METHOD = "method"
        const val MINER = "addr"
        const val FROM = "from"
    }
}