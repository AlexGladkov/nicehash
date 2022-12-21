package com.dev.nicehash.app.servers

import android.util.Log
import com.dev.nicehash.domain.models.ExchangeRate
import com.dev.nicehash.domain.repositories.CurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Alex Gladkov on 13.08.18.
 * Implementation for ExchangeServer
 */
class ExchangeServerImpl(val currencyRepository: CurrencyRepository): ExchangeServer {
    private val TAG = ExchangeServerImpl::class.java.simpleName

    public enum class ExchangeCode(val value: String) {
        Ru("RUB"), US("USD")
    }

    companion object {
        private val data = ArrayList<ExchangeRate>()
    }

    init {
        currencyRepository.fetchExchangeRatesFromDB()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.addAll(it)

                    fetchExchange()
                }, {
                    fetchExchange()
                })
    }

    private fun fetchExchange() {
        currencyRepository.fetchExchangeRates()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    data.clear()
                    data.addAll(response)
                }, {

                })
    }

    override fun convertBtcFor(code: String, value: Double): Float {
        val rate = data.firstOrNull { it.code == code }
        return if (rate == null) {
            -1f
        } else {
            (rate.value * value).toFloat()
        }
    }

    override fun fetchRate(code: String): Float {
        val rate = data.firstOrNull { it.code == code }
        return rate?.value?.toFloat() ?: -1f
    }
}