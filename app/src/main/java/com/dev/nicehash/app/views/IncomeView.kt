package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.dev.nicehash.domain.models.Income

/**
 * Created by Alex Gladkov on 15.07.18.
 * View for income tab
 */
interface IncomeView: MvpView {
    fun setupIncome(income: Income, currencyRate: Float, usdRate: Float)
}