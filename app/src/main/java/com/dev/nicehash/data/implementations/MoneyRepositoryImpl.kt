package com.dev.nicehash.data.implementations

import android.util.Log
import com.dev.nicehash.data.providers.NicehashProvider
import com.dev.nicehash.data.providers.models.ApiAlgo
import com.dev.nicehash.domain.models.Income
import com.dev.nicehash.domain.repositories.MoneyRepository
import com.dev.nicehash.helpers.MathUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.util.*

/**
 * Created by Alex Gladkov on 11/10/2018.
 * Money repository implementation
 */
class MoneyRepositoryImpl(val nicehashProvider: NicehashProvider) : MoneyRepository {
    private val TAG = MoneyRepositoryImpl::class.java.simpleName

    /**
     * Miner unpaid balance
     * @param miner - wallet BTC address
     * @return single wrap of unpaid balance value
     */
    override fun fetchUnpaidMoney(miner: String): Single<Double> {
        return nicehashProvider.fetchStatsProviderEx(miner = miner, from = 0L)
                .flatMap { result ->
                    Single.just(result.current.sumByDouble { it.data.unpaid })
                }
    }

    /**
     * Miner income for week, day, month, year
     * @param miner - wallet BTC address
     * @return single wrap of Income model
     */
    override fun fetchMinerIncome(miner: String): Single<Income> {
        return nicehashProvider.fetchStatsProviderEx(miner = miner, from = DateTime().minusHours(25).millis / 1000)
                .flatMap { result ->
                    val profitabilityItems = LinkedHashMap<Int, ApiAlgo>()
                    val chartItems = LinkedHashMap<Long, Double>()

                    result.current.forEach { current ->
                        profitabilityItems[current.algo] = ApiAlgo(speed = current.data.a.toDouble(),
                                price = current.profitability)
                    }

                    result.past.forEach { past ->
                        past.data.forEach { data ->
                            val timestamp = data.time * 300
                            val localDateTime = DateTime(timestamp * 1000).millis

                            val speedObject = data.a
                            val profitability = (profitabilityItems[past.algo]?.price ?: 0.0) *
                                    (profitabilityItems[past.algo]?.speed ?: 0.0)
                            val currentSpeed = profitabilityItems[past.algo]?.speed ?: 0.0
                            val multi = speedObject / currentSpeed
                            val pastProfitability = multi * profitability

                            if (chartItems.containsKey(localDateTime)) {
                                chartItems[localDateTime] = (chartItems[localDateTime] ?: 0.0) + pastProfitability
                            } else {
                                chartItems[localDateTime] = pastProfitability
                            }
                        }
                    }

                    val dayDuration = DateTime().minusDays(1).millis
                    val dayArray = chartItems.filter { it.key > dayDuration }


                    Single.just(Income(hourValue = 0.0,
                            hourBtc = MathUtils.getAverage(collection = dayArray.values) / 24, dayValue = 0.0,
                            dayBtc = MathUtils.getAverage(collection = dayArray.values), monthValue = 0.0,
                            monthBtc = MathUtils.getAverage(collection = dayArray.values) * 31,
                            weekValue = 0.0, weekBtc = MathUtils.getAverage(collection = dayArray.values) * 7))
                }
    }
}