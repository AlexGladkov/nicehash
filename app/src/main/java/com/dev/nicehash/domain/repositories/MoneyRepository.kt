package com.dev.nicehash.domain.repositories

import com.dev.nicehash.domain.models.Income
import io.reactivex.Single
import java.util.*

/**
 * Created by Alex Gladkov on 11/10/2018.
 * Repository for API linked with money and DB
 */
interface MoneyRepository {
    fun fetchMinerIncome(miner: String): Single<Income>
    fun fetchUnpaidMoney(miner: String): Single<Double>
}