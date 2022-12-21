package com.dev.nicehash.app.presenters

import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.app.views.PayoutsView
import com.dev.nicehash.domain.models.Payout
import com.dev.nicehash.domain.repositories.MinerRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.concurrent.thread

/**
 * Created by Alex Gladkov on 15.07.18.
 * Presenter for payouts tab
 */
@InjectViewState
class PayoutsPresenter(val minerRepository: MinerRepository, val exchangeServer: ExchangeServer) : MvpPresenter<PayoutsView>() {
    private val TAG = PayoutsPresenter::class.java.simpleName

    fun fetchPayouts(miner: String, id: Int, isRefresh: Boolean) {
        if (id < 0) {
            val array = ArrayList<Payout>()
            array.add(Payout(dateTime = "7/03 • 13:37", fee = 2f, feeBtc = 0.00000727, payBtc = 0.000323))
            array.add(Payout(dateTime = "6/03 • 13:02", fee = 2f, feeBtc = 0.00003425, payBtc = 0.000341))
            array.add(Payout(dateTime = "5/03 • 12:52", fee = 2f, feeBtc = 0.00006123, payBtc = 0.000552))
            viewState.setupPayouts(data = array)
        } else {
            if (isRefresh) viewState.startLoading()
            minerRepository.fetchMinerPayments(miner = miner)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        viewState.endLoading()
                        if (response.isEmpty()) {
                            viewState.setupNoItems()
                        } else {
                            viewState.setupPayouts(data = response)
                            val handler = Handler()
                            thread {
                                val sum: Double = response.sumByDouble { it.payBtc }
                                val usd = exchangeServer.convertBtcFor(code = ExchangeServerImpl.ExchangeCode
                                        .US.value, value = sum)

                                handler.post {
                                    viewState.setupBarInfo(paidBTC = sum, usd = usd)
                                }
                            }
                        }
                    }, { error ->
                        viewState.endLoading()
                        viewState.showError(message = error.localizedMessage)
                    })
        }
    }
}