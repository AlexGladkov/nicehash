package com.dev.nicehash.data.providers

import com.dev.nicehash.data.providers.models.ApiBalance
import com.dev.nicehash.data.providers.models.ApiPayment
import com.dev.nicehash.data.providers.models.ApiWorker
import com.dev.nicehash.domain.models.ChartItem
import io.reactivex.Single

/**
 * Created by Alex Gladkov on 05.08.18.
 * Provider for accessing base miner info
 */
interface MinerProvider {
    fun getStatistics(miner: String, time: Long)

    /**
     * @return array of chart items to display
     * @param miner - wallet id
     * @param time - start position
     * @param elapsedHours - how many hours needs to display
     */
    fun getChart(miner: String, time: Long, elapsedHours: Int): Single<List<ChartItem>>

    fun getPayments(miner: String): Single<List<ApiPayment>>
    fun getWorkers(miner: String): Single<List<ApiWorker>>
    fun getBalance(miner: String): Single<List<ApiBalance>>
}