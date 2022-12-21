package com.dev.nicehash.data.providers.implementations

import android.util.Log
import com.dev.nicehash.data.providers.MinerProvider
import com.dev.nicehash.data.providers.models.*
import com.dev.nicehash.data.providers.services.RemoteMinerService
import com.dev.nicehash.domain.models.ChartItem
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

/**
 * Created by Alex Gladkov on 05.08.18.
 * Provider implementation for Miner provider interface
 */
class MinerProviderImpl(private val remoteMinerService: RemoteMinerService,
                        private val gson: Gson) : MinerProvider {
    private val TAG = MinerProviderImpl::class.java.simpleName
    private val TIME_CAPACITY = 6

    override fun getChart(miner: String, time: Long, elapsedHours: Int): Single<List<ChartItem>> {
        return Single.create { subscriber ->
            remoteMinerService.getWorkerHistory(method = "stats.provider.ex", miner = miner, from = time)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ chartResponse ->
                        val past = chartResponse.result.get("past")?.asJsonArray
                        val current = chartResponse.result.get("current")?.asJsonArray
                        val profitabilityItems = LinkedHashMap<Int, ApiAlgo>()
                        val chartItems = LinkedHashMap<Long, Double>()

                        current?.forEach { algo ->
                            val accepted = algo.asJsonObject.get("data")!!.asJsonArray[0]?.asJsonObject?.get("a")
                            accepted?.let { acceptedElement ->
                                profitabilityItems[algo.asJsonObject.get("algo")?.asInt
                                        ?: -1] = ApiAlgo(speed = acceptedElement.asDouble,
                                        price = algo.asJsonObject.get("profitability")?.asDouble
                                                ?: 0.0)
                            }
                        }

                        past?.forEach { it ->
                            val data = it.asJsonObject.get("data").asJsonArray
                            val algo = it.asJsonObject.get("algo")?.asInt ?: -2
                            data.forEach { dataItem ->
                                val timestamp = dataItem.asJsonArray[0].asLong * 300
                                val localDateTime = DateTime(timestamp * 1000).millis

                                val speedObject = dataItem.asJsonArray[1].asJsonObject
                                speedObject.get("a")?.asDouble?.let { value ->
                                    val profitability = (profitabilityItems[algo]?.price ?: 0.0) *
                                            (profitabilityItems[algo]?.speed ?: 0.0)
                                    val currentSpeed = profitabilityItems[algo]?.speed ?: 0.0
                                    val multi = value / currentSpeed
                                    val pastProfitability = multi * profitability

                                    if (chartItems.containsKey(localDateTime)) {
                                        chartItems[localDateTime] = (chartItems[localDateTime] ?: 0.0) + pastProfitability
                                    } else {
                                        chartItems[localDateTime] = pastProfitability
                                    }
                                }
                            }
                        }

                        val tempData = LinkedList<ChartItem>()
                        chartItems.keys.forEach { key ->
                            tempData.add(ChartItem(x = key / 1000, y = chartItems[key] ?: 0.0))
                        }

                        subscriber.onSuccess(tempData)

//                        val chartArray: MutableList<ChartItem> = ArrayList(TIME_CAPACITY)
//                        val chartItems = LinkedHashMap<Long, Double>()
//                        val profitabilityItems = LinkedHashMap<Int, Double>()
//                        val past = response.result.get("past")?.asJsonArray
//                        val current = response.result.get("current")?.asJsonArray
//                        var iterator = 0
//
//                        while (iterator < TIME_CAPACITY) {
//                            iterator++
//                            chartArray.add(ChartItem(x = 0, y = 0.0))
//                        }
//
////                        remoteMinerService.getStatistics(method = "stats.global.24h", miner = miner)
////                                .subscribeOn(Schedulers.computation())
////                                .observeOn(AndroidSchedulers.mainThread())
////                                .subscribe { response2 ->
////                                    val stats = response2.result.get("stats")?.asJsonArray
////                                    val profitabilityItems2 = LinkedHashMap<Int, Double>()
////                                    Log.e(TAG, "average $response2")
////
////                                    stats?.forEach { it ->
////                                        profitabilityItems2[it.asJsonObject.get("algo")?.asInt
////                                                ?: -1] =
////                                                (it.asJsonObject.get("price")?.asDouble ?: -1.0) /
////                                                (it.asJsonObject.get("speed")?.asDouble ?: -1.0)
////                                    }
////
////                                    Log.e(TAG, "profitability 2 $profitabilityItems2")
////
////                                    profitabilityItems2.keys.forEach {
////                                        Log.e(TAG, "key average $it, value ${DecimalFormat("##.##########")
////                                                .format(profitabilityItems2[it])}")
////                                    }
////
////
////                                }
//
//                        current?.forEach { it ->
//                            profitabilityItems[it.asJsonObject.get("algo")?.asInt ?: -1] =
//                                    it.asJsonObject.get("profitability")?.asDouble ?: 0.0
//                        }
//
//                        profitabilityItems[14] = 0.0009 / 496.78
//                        profitabilityItems[20] = 0.0003 / 86.67
//
//                        profitabilityItems.keys.forEach {
//                            Log.e(TAG, "key $it, value ${DecimalFormat("##.##########")
//                                    .format(profitabilityItems[it])}")
//                        }
//
//                        past?.forEach { it ->
//                            val data = it.asJsonObject.get("data").asJsonArray
//                            val algo = it.asJsonObject.get("algo")?.asInt ?: -2
//                            data.forEach { dataItem ->
//                                val timestamp = dataItem.asJsonArray[0].asLong * 300
//                                val localDateTime = DateTime(timestamp * 1000).millis
////                                Log.e(TAG, "date ${DateTime(timestamp * 1000)}")
//
//                                if (localDateTime > time) {
//                                    val speedObject = dataItem.asJsonArray[1].asJsonObject
//                                    speedObject.get("a")?.asDouble?.let { value ->
//                                        Log.e(TAG, "value $value for algo $algo")
//                                        //                                                    Log.e(TAG, "value $value, profitability " +
////                                                            DecimalFormat("##.##########").format(profitabilityItems2[algo]))
//                                        if (chartItems.containsKey(localDateTime)) {
//                                            chartItems[localDateTime] = (chartItems[localDateTime]
//                                                    ?: 0.0)
//                                            + value * (profitabilityItems[algo] ?: 1.0)
//                                        } else {
//                                            chartItems[localDateTime] = value * (profitabilityItems[algo] ?: 1.0)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        val tempData = LinkedList<ChartItem>()
//
//                        chartItems.keys.forEach { key ->
//                            tempData.add(ChartItem(x = key / 1000, y = chartItems[key] ?: 0.0))
//                            val stepInMinutes = (elapsedHours * 60) / TIME_CAPACITY
//                            val difference = DateTime().millis - key
//                            val differenceInMinutes = (difference / (1000 * 60))
//                            val index = (differenceInMinutes / stepInMinutes).toInt()
//                            if (index < TIME_CAPACITY) {
////                                if (chartArray[index].x == 0L) {
////                                    chartArray[index].x = key / 1000
////                                    chartArray[index].y = chartItems[key] ?: 0.0
////                                } else {
////                                    chartArray[index].y += chartItems[key] ?: 0.0
////                                }
////                                Log.e(TAG, "value ${DecimalFormat("##.###############").format(chartItems[key])}")
//                                chartArray[index].x = key / 1000
//                                chartArray[index].y = chartItems[key] ?: 0.0
//                            }
//                        }
//
//                        tempData.forEach {
//                            Log.e(TAG, "x - ${it.x}, y - ${it.y}")
//                        }
//                        subscriber.onSuccess(tempData)

                    }, {
                        subscriber.onError(it)
                    })
        }
    }

    override fun getStatistics(miner: String, time: Long) {


//        Single.zip(list, { response ->
//            Log.e(TAG, "1 $response")
//            val result = LinkedList<Double>()
//            Log.e(TAG, "2 $result")
//            response.forEach {
//                try {
//                    var profitAbility = 0.0
//                    (it as? ApiResponse)?.result?.get("current")?.asJsonArray?.forEach { element ->
//                        profitAbility += element.asJsonObject.get("profitability").asDouble
//                    }
//
//                    result.add(profitAbility)
//                } catch (e: Exception) {
//                    Log.e(TAG, "3.1 ${e.localizedMessage}")
//                }
//            }
//            Log.e(TAG, "3 $result")
//            result
//        })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ response ->
//                    response.forEach {
//                        Log.e(TAG, "response ${DecimalFormat("##.#########").format(it)}")
//                    }
//                }, { error ->
//
//                })

//        remoteMinerService.getWorkerHistory(method = "stats.provider.ex", miner = miner, from = time)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { response ->
//                    val current = response.result.get("current").asJsonArray
//                    current.forEach {  it ->
//                        Log.e(TAG, "response ${it.asJsonObject.get("profitability").asString}")
//                    }
//                }
    }

    override fun getBalance(miner: String): Single<List<ApiBalance>> {
        return remoteMinerService.getStatistics(method = "stats.provider.ex", miner = miner)
                .flatMap {
                    val current = it.result.get("current").asJsonArray
                    if (current == null) {
                        Single.error<List<ApiBalance>>(NullPointerException())
                    } else {
                        val converted = ArrayList<ApiBalance>(current.size())
                        current.forEach {
                            converted.add(gson.fromJson(it.asJsonObject, ApiBalance::class.java))
                        }
                        Single.just(converted)
                    }
                }
    }

    override fun getWorkers(miner: String): Single<List<ApiWorker>> {
        return remoteMinerService.getWorkers(method = "stats.provider.workers", miner = miner)
                .flatMap {
                    val workers = it.result.get("workers").asJsonArray
                    if (workers == null) {
                        Single.error<List<ApiWorker>>(NullPointerException())
                    } else {
                        val converted = ArrayList<ApiWorker>(workers.size())
                        workers.forEach {
                            converted.add(ApiWorker(it.asJsonArray[0].asString,
                                    it.asJsonArray[2].asInt))
                        }
                        Single.just(converted)
                    }
                }
    }

    override fun getPayments(miner: String): Single<List<ApiPayment>> {
        return remoteMinerService.getStatistics(method = "stats.provider", miner = miner)
                .flatMap {
                    val payments = it.result.get("payments").asJsonArray
                    if (payments == null) {
                        Single.error<List<ApiPayment>>(NullPointerException())
                    } else {
                        val converted = ArrayList<ApiPayment>(payments.size())
                        payments.forEach { converted.add(gson.fromJson(it.asJsonObject, ApiPayment::class.java)) }
                        Single.just(converted)
                    }
                }
    }
}