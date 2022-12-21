package com.dev.nicehash.data.providers.implementations

import android.util.Log
import com.dev.nicehash.data.providers.NicehashProvider
import com.dev.nicehash.data.providers.models.*
import com.dev.nicehash.data.providers.services.RemoteMinerService
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

/**
 * Created by Alex Gladkov on 11/10/2018.
 * Implementation for NiceHash provider interface
 */
class NicehashProviderImpl(val remoteMinerService: RemoteMinerService): NicehashProvider {
    private val TAG = NicehashProviderImpl::class.java.simpleName
    private val gson = Gson()

    private var lastProviderEx = 0L

    /**
     * Get current profitability (price) and hashing speed for all algorithms. Refreshed every 30 seconds.
     */
    override fun fetchGlobalCurrent(): Single<ApiGlobalCurrent> {
        return remoteMinerService.getStatsGlobalCurrent()
                .flatMap {
                    val stats = it.result.get("stats")?.asJsonArray
                    if (stats == null) {
                        Single.error(NullPointerException())
                    } else {
                        val list = LinkedList<ApiGlobalCurrentStats>()
                        stats.forEach {
                            list.add(gson.fromJson(it, ApiGlobalCurrentStats::class.java))
                        }
                        Single.just(ApiGlobalCurrent(method = it.method, result = list))
                    }
                }
    }

    /**
     * Get average profitability (price) and hashing speed for all algorithms in past 24 hours.
     */
    override fun fetchGlobal24(): Single<ApiGlobal24> {
           return remoteMinerService.getStatsGlobal24H()
                   .flatMap {
                       val stats = it.result.get("stats")?.asJsonArray
                       if (stats == null) {
                           Single.error(NullPointerException())
                       } else {
                           val list = LinkedList<ApiGlobal24Stats>()
                           stats.forEach {
                               list.add(gson.fromJson(it, ApiGlobal24Stats::class.java))
                           }
                           Single.just(ApiGlobal24(method = it.method, result = list))
                       }
                   }
    }

    /**
     * Get current stats for provider for all algorithms. Refreshed every 30 seconds. It also returns past 56 payments.
     * @param miner - Provider's BTC address
     */
    override fun fetchStatsProvider(miner: String): Single<ApiProvider> {
        return remoteMinerService.getStatsProvider(miner = miner)
                .flatMap {
                    val stats = it.result.get("stats")?.asJsonArray
                    if (stats == null) {
                        Single.error(NullPointerException())
                    } else {
                        val list = LinkedList<ApiProviderStats>()
                        stats.forEach {
                            list.add(gson.fromJson(it, ApiProviderStats::class.java))
                        }
                        Single.just(ApiProvider(method = it.method, result = list))
                    }
                }
    }

    /**
     * Get detailed stats for provider for all algorithms including history data and payments in past 7 days.
     * @param miner - Provider's BTC address
     * @param from - Get history data from this time (UNIX timestamp). This parameter is optional and is by default considered to be 0 (return complete history).
     */
    override fun fetchStatsProviderEx(miner: String, from: Long): Single<ApiProviderEx> {
        return remoteMinerService.getStatsProviderEx(miner = miner, from = from)
                .cache()
                .flatMap {
                    val current = it.result.get("current")?.asJsonArray
                    val past = it.result.get("past")?.asJsonArray
                    val payments = it.result.get("payments")?.asJsonArray
                    val addr = it.result.get("addr")?.asString
                    if (current == null || past == null || payments == null || addr == null) {
                        Single.error(NullPointerException())
                    } else {
                        val currentData = LinkedList<ApiProviderCurrent>()
                        val pastData = LinkedList<ApiProviderPast>()
                        current.forEach {
                            val dataItem = it.asJsonObject.get("data").asJsonArray
                            currentData.add(ApiProviderCurrent(profitability = it.asJsonObject.get("profitability").asDouble,
                                    suffix = it.asJsonObject.get("suffix").asString,
                                    name = it.asJsonObject.get("name").asString,
                                    algo = it.asJsonObject.get("algo").asInt,
                                    data = ApiProviderData(a = dataItem[0].asJsonObject.get("a").asFloat,
                                            rs = dataItem[0].asJsonObject.get("rs")?.asFloat ?: 0f,
                                            unpaid = dataItem[1].asDouble)))
                        }

                        past.forEach {
                            val data = LinkedList<ApiProviderPastData>()
                            val jsonData = it.asJsonObject.get("data").asJsonArray

                            try {
                                jsonData.forEach { dataItem ->
                                    data.add(ApiProviderPastData(time = dataItem.asJsonArray[0].asLong,
                                            a = dataItem.asJsonArray[1].asJsonObject.get("a").asFloat,
                                            unpaid = dataItem.asJsonArray[2].asDouble))
                                }
                            } catch (e: Exception) {
                                // TODO smth
                            }

                            pastData.add(ApiProviderPast(algo = it.asJsonObject.get("algo").asInt,
                                    data = data))
                        }

                        Single.just(ApiProviderEx(method = it.method, current = currentData,
                                past = pastData, payments = LinkedList(), addr = addr))
                    }
                }
    }

    /**
     * Get payments for provider.
     * @param miner - Provider's BTC address
     * @param from - Get history data from this time (UNIX timestamp). This parameter is optional and is by default considered to be 0 (return complete history).
     */
    override fun fetchStatsProviderPayments(miner: String, from: Long): Single<ApiPayments> {
        return remoteMinerService.getStatsProviderPayments(miner = miner, from = from)
                .flatMap {
                    val payments = it.result.get("payments")?.asJsonArray
                    val addr = it.result.get("addr")?.asString
                    val nh_wallet = it.result.get("nh_wallet")?.asBoolean
                    if (payments == null || addr == null || nh_wallet == null) {
                        Single.error(NullPointerException())
                    } else {
                        val list = LinkedList<ApiPaymentsData>()
                        payments.forEach {
                            list.add(gson.fromJson(it, ApiPaymentsData::class.java))
                        }
                        Single.just(ApiPayments(method = it.method, addr = addr, nh_wallet = nh_wallet,
                                payments = list))
                    }
                }
    }
}