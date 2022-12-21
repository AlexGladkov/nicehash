package com.dev.nicehash.data.providers

/**
 * Created by Alex Gladkov on 05.08.18.
 * Provider for general requests (ex. api version, enc)
 */
interface GeneralProvider {
    fun getVersion()
}