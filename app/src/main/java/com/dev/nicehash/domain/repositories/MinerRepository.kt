package com.dev.nicehash.domain.repositories

import com.dev.nicehash.data.providers.models.ApiPayment
import com.dev.nicehash.domain.models.*
import io.reactivex.Single

/**
 * Created by Alex Gladkov on 23.06.18.
 * Repository for Miners API and DB
 */
interface MinerRepository {
    fun loadMinersFromDb(minerId: Int): Single<List<Miner>>
    fun addMiner(miner: Miner): Single<Long>
    fun updateMiner(miner: Miner?, newName: String, newHash: String): Single<Boolean>
    fun fetchMinerStatistics()
    fun fetchMinerPayments(miner: String): Single<List<Payout>>
    fun fetchMinerBalance(miner: String): Single<List<Balance>>
    fun fetchWorkers(miner: String): Single<List<WorkerHub>>
    fun fetchMinerChart(miner: String, time: Long, elapsedHours: Int): Single<List<ChartItem>>
}