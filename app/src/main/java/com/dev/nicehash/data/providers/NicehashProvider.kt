package com.dev.nicehash.data.providers

import com.dev.nicehash.data.providers.models.*
import io.reactivex.Single

/**
 * Created by Alex Gladkov on 11/10/2018.
 * Provider for all api of NiceHash service
 */
interface NicehashProvider {
    fun fetchGlobalCurrent(): Single<ApiGlobalCurrent>
    fun fetchGlobal24(): Single<ApiGlobal24>
    fun fetchStatsProvider(miner: String): Single<ApiProvider>
    fun fetchStatsProviderEx(miner: String, from: Long): Single<ApiProviderEx>
    fun fetchStatsProviderPayments(miner: String, from: Long): Single<ApiPayments>
}