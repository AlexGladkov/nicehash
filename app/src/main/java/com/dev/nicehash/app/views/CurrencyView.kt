package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dev.nicehash.domain.models.Currency

/**
 * Created by Alex Gladkov on 25.07.18.
 * View for currency activity
 * @see .app.activities.CurrenciesActivity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface CurrencyView: MvpView {
    fun setupView(data: List<Currency>)
}