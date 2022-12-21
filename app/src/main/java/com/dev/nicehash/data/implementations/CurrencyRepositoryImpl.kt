package com.dev.nicehash.data.implementations

import android.util.Log
import com.dev.nicehash.data.converters.ExchangeRateConverter
import com.dev.nicehash.data.providers.CurrencyProvider
import com.dev.nicehash.data.room.RoomAppDataSource
import com.dev.nicehash.domain.models.ExchangeRate
import com.dev.nicehash.domain.repositories.CurrencyRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.concurrent.thread

/**
 * Created by Alex Gladkov on 11.08.18.
 * Implementation for currency repository
 */
class CurrencyRepositoryImpl(private val exchangeRateConverter: ExchangeRateConverter,
                             private val exchangeRateProvider: CurrencyProvider,
                             private val appDataSource: RoomAppDataSource) : CurrencyRepository {
    private val TAG = CurrencyRepositoryImpl::class.java.simpleName

    override fun fetchExchangeRatesFromDB(): Single<List<ExchangeRate>> {
        return Single.create { subscriber ->
            val exchanges = appDataSource.currencyDao.fetchCurrencies()
                    .map {  exchangeRateConverter.dbToModel(currencyEntity = it) }
            if (exchanges.isNotEmpty()) {
                subscriber.onSuccess(exchanges)
            } else {
                subscriber.onError(NullPointerException())
            }
        }
    }

    override fun fetchExchangeRates(): Single<List<ExchangeRate>> {
        return Single.create { subscriber ->
            exchangeRateProvider.fetchExchangeRates()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        subscriber.onSuccess(response.map { exchangeRateConverter.apiToModel(apiExchange = it) })

                        response.forEach {
                            thread {
                                appDataSource.currencyDao.updateCurrency(
                                        currencyEntity = exchangeRateConverter.apiToDB(apiExchange = it))
                            }
                        }
                    }, { error ->
                        subscriber.onError(error)
                    })
        }
    }

    override fun updateExchangeRate(exchangeRate: ExchangeRate): Single<Boolean> {
        return Single.just(true)
    }
}