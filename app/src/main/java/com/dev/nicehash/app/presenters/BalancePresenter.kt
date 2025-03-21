package com.dev.nicehash.app.presenters

import android.util.Log
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.app.views.BalanceView
import com.dev.nicehash.domain.models.Balance
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.domain.repositories.MoneyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.text.DecimalFormat

/**
 * Created by Alex Gladkov on 15.07.18.
 * Presenter for Balance tab
 */
@InjectViewState
class BalancePresenter(val minerRepository: MinerRepository,
                       val exchangeServer: ExchangeServer, val moneyRepository: MoneyRepository,
                       val currency: ExchangeServerImpl.ExchangeCode): MvpPresenter<BalanceView>() {
    private val TAG = BalancePresenter::class.java.simpleName

    fun fetchUnpaid(miner: String, id: Int) {
        moneyRepository.fetchUnpaidMoney(miner = miner)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ unpaid ->
                    val usd = exchangeServer.convertBtcFor(ExchangeServerImpl.ExchangeCode.US.value, unpaid)
                    viewState.setupBarItems(unpaid = DecimalFormat("##.######").format(unpaid),
                            usd = DecimalFormat("##.##").format(usd))
                }, { error ->
                    error.printStackTrace()
                })
    }

    fun fetchBalance(miner: String, id: Int, isRefresh: Boolean) {
        if (id < 0) {
            val array = ArrayList<Balance>()
            array.add(Balance(id = 0, title = "DaggerHashimoto",
                    value = exchangeServer.convertBtcFor(currency.value, 0.00000025),
                    btc = 0.00000025, incomeBtc = 0.00005,
                    incomeValue = exchangeServer.convertBtcFor(currency.value, 0.00005),
                    suffix = "Mh"))
            array.add(Balance(id = 0, title = "Equihash", value = exchangeServer.convertBtcFor(currency.value, 0.000004256),
                    btc = 0.000004256, incomeBtc = 0.0017, incomeValue = exchangeServer.convertBtcFor(currency.value, 0.0017),
                    suffix = "Mh"))
            array.add(Balance(id = 0, title = "Pascal", value = exchangeServer.convertBtcFor(currency.value, 0.000000078),
                    btc = 0.000000078, incomeBtc = 0.0003252, incomeValue = exchangeServer.convertBtcFor(currency.value, 0.0003252),
                    suffix = "Mh"))
            viewState.setupBalance(data = array)
        } else {
            if (isRefresh) viewState.startLoading()
            minerRepository.fetchMinerBalance(miner = miner)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        val result = response.map {
                            it.incomeValue = exchangeServer.convertBtcFor(code = currency.value, value = it.incomeBtc)
                            it.value = exchangeServer.convertBtcFor(code = currency.value, value = it.btc)
                            it
                        }

                        if (result.isEmpty()) {
                            viewState.showNoItems()
                        } else {
                            viewState.setupBalance(data = result)
                        }

                        viewState.endLoading()
                    }, { error ->
                        viewState.showError(message = error.localizedMessage)
                        viewState.endLoading()
                    })
        }
    }
}