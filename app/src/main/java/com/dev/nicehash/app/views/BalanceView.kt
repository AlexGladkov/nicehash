package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dev.nicehash.domain.models.Balance

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