package com.dev.nicehash.data.implementations

import android.util.Log
import com.dev.nicehash.data.converters.BalanceConverter
import com.dev.nicehash.data.converters.MinerConverter
import com.dev.nicehash.data.converters.PaymentsConverter
import com.dev.nicehash.data.converters.WorkerConverter
import com.dev.nicehash.data.providers.MinerProvider
import com.dev.nicehash.data.providers.models.ApiPayment
import com.dev.nicehash.data.room.RoomAppDataSource
import com.dev.nicehash.data.room.models.MinerEntity
import com.dev.nicehash.domain.models.*
import com.dev.nicehash.domain.repositories.MinerRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alex Gladkov on 23.06.18.
 * Repository for any miners activity
 */
class MinerRepositoryImpl(val appDataSource: RoomAppDataSource, val minerProvider: MinerProvider,
                          private val minerConverter: MinerConverter, private val paymentsConverter: PaymentsConverter,
                          private val balanceConverter: BalanceConverter, private val workerConverter: WorkerConverter) : MinerRepository {
    private val TAG: String = MinerRepositoryImpl::class.java.simpleName

    override fun fetchMinerStatistics() {
        minerProvider.getBalance(miner = "3Aqj4o27ixprba67GvBctDkXijxxpbD4hS")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //                    Log.e(TAG, "response $response")
                }, { error ->
                    //                    Log.e(TAG, "error $error")
                })
    }

    override fun fetchMinerBalance(miner: String): Single<List<Balance>> {
        return minerProvider.getBalance(miner = miner)
                .flatMap { response -> Single.just(response.map { model -> balanceConverter.apiToModel(apiBalance = model) }) }
    }

    override fun fetchWorkers(miner: String): Single<List<WorkerHub>> {
        return minerProvider.getWorkers(miner = miner)
                .flatMap { response ->
                    Single.just(
                            workerConverter.workerToHub(response.map { model -> workerConverter.apiToModel(apiWorker = model) }))
                }
    }

    override fun fetchMinerPayments(miner: String): Single<List<Payout>> {
        return minerProvider.getPayments(miner = miner)
                .flatMap { response -> Single.just(response.map { model -> paymentsConverter.apiToModel(apiPayment = model) }) }
    }

    override fun fetchMinerChart(miner: String, time: Long, elapsedHours: Int): Single<List<ChartItem>> {
        return minerProvider.getChart(miner = miner, time = time, elapsedHours = elapsedHours)

    }

    override fun loadMinersFromDb(minerId: Int): Single<List<Miner>> {
        return Single.create { subscriber ->
            try {
                val miners = appDataSource.minerDao.getMiners()

                val result = ArrayList<Miner>()
                if (miners.isEmpty()) {
                    result.add(Miner(id = -1, name = "Demo Miner", hash = "Your BTC wallet address", isSelected = false))
                } else {
                    result.addAll(miners.map { minerConverter.dbToModel(minerEntity = it, minerId = minerId) }
                            .sortedWith(compareBy { it.isSelected })
                            .reversed())
                }

                subscriber.onSuccess(result)
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }

    override fun updateMiner(miner: Miner?, newName: String, newHash: String): Single<Boolean> {
        return Single.create { subscriber ->
            if (miner == null) subscriber.onError(Throwable(NullPointerException()))
            try {
                appDataSource.minerDao.addMiner(MinerEntity(id = miner?.id, name = newName, hash = newHash,
                        payouts = ""))
                subscriber.onSuccess(true)
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }

    override fun addMiner(miner: Miner): Single<Long> {
        return Single.create { subscriber ->
            try {
                subscriber.onSuccess(appDataSource.minerDao.addMiner(MinerEntity(id = null, name = miner.name,
                        hash = miner.hash, payouts = "")))
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }
}