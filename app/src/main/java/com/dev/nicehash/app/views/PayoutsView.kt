package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dev.nicehash.domain.models.Payout

/**
 * Created by Alex Gladkov on 15.07.18.
 * View for payouts tab
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface PayoutsView: MvpView {
    fun setupPayouts(data: List<Payout>)
    fun setupNoItems()
    fun showError(message: String)
    fun startLoading()
    fun endLoading()
    fun setupBarInfo(paidBTC: Double, usd: Float)
}