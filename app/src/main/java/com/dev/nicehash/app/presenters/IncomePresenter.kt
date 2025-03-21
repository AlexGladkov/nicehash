package com.dev.nicehash.app.presenters

import android.annotation.SuppressLint
import android.util.Log
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.app.views.IncomeView
import com.dev.nicehash.domain.models.Income
import com.dev.nicehash.domain.repositories.CurrencyRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.domain.repositories.MoneyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by Alex Gladkov on 15.07.18.
 * Presenter for income tab
 */
@InjectViewState
class IncomePresenter(private val exchangeServer: ExchangeServer,
                      private val repository: MoneyRepository): MvpPresenter<IncomeView>() {

    @SuppressLint("CheckResult")
    fun fetchIncome(currency: ExchangeServerImpl.ExchangeCode, miner: String) {
        repository.fetchMinerIncome(miner = miner)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.monthValue = exchangeServer.convertBtcFor(code = currency.value, value = result.monthBtc).toDouble()
                    result.weekValue = exchangeServer.convertBtcFor(code = currency.value, value = result.weekBtc).toDouble()
                    result.dayValue = exchangeServer.convertBtcFor(code = currency.value, value = result.dayBtc).toDouble()
                    result.hourValue = exchangeServer.convertBtcFor(code = currency.value, value = result.hourBtc).toDouble()
                    viewState.setupIncome(income = result, usdRate = exchangeServer.fetchRate(code = ExchangeServerImpl
                            .ExchangeCode.US.value), currencyRate = exchangeServer.fetchRate(code = currency.value))
                }, { error ->
                    error.printStackTrace()
                })

//        viewState.setupIncome(income = Income(hourBtc = 0.000534, hourValue = exchangeServer
//                .convertBtcFor(code = currency.value, value = 0.000534).toDouble(),
//                dayBtc = 0.02323, dayValue = exchangeServer.convertBtcFor(currency.value,
//                value = 0.02323).toDouble(), weekBtc = 0.004544, weekValue = exchangeServer
//                .convertBtcFor(currency.value, value = 0.004544).toDouble(),
//                monthBtc = 0.00343, monthValue = exchangeServer.convertBtcFor(currency.value,
//                value = 0.00343).toDouble()), usdRate = exchangeServer.fetchRate(code = ExchangeServerImpl.ExchangeCode.US.value),
//                currencyRate = exchangeServer.fetchRate(code = currency.value))
    }
}