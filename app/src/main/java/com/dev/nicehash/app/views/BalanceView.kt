package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Balance
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 15.07.18.
 * View for balance tab
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface BalanceView: MvpView {
    fun setupBalance(data: List<Balance>)
    fun startLoading()
    fun endLoading()
    fun showNoItems()
    fun showError(message: String)
    fun setupBarItems(unpaid: String, usd: String)
}