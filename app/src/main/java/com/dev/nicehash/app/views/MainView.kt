package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Configuration
import com.github.mikephil.charting.data.Entry
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 21.06.18.
 * View interface for MainActivity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface MainView: MvpView {
    fun setupNavigation(position: Int)
    fun setupConfiguration(configuration: Configuration)
    fun setupChartView(position: Int)
    fun setupChartData(data: List<Entry>)
    fun setupChartHeight(value: Float)
    fun setupIntroBar(introBarPosition: Float)
    fun setupTabBar(tabBarPosition: Float)
    fun displayNavigationInfo(leftValue: String, rightValue: String)
    fun showError(message: Int)
    fun showError(message: String)
}