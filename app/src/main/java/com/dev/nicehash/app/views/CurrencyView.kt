package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Currency
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 25.07.18.
 * View for currency activity
 * @see .app.activities.CurrenciesActivity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CurrencyView: MvpView {
    fun setupView(data: List<Currency>)
}