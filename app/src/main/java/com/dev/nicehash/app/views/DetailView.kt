package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.mikephil.charting.data.Entry

/**
 * Created by Alex Gladkov on 30.07.18.
 * View for Detail Activity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface DetailView: MvpView {
    fun setupChartView(position: Int)
    fun setupChartData(data: List<Entry>)
}