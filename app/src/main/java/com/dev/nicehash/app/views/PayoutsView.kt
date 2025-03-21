package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Payout
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

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