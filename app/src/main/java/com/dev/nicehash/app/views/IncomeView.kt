package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Income
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 15.07.18.
 * View for income tab
 */
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface IncomeView: MvpView {
    fun setupIncome(income: Income, currencyRate: Float, usdRate: Float)
}