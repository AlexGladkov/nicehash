package com.dev.nicehash.app.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PurchaseView: MvpView {
    fun showError(message: Int)
    fun makeTestBuy()
}