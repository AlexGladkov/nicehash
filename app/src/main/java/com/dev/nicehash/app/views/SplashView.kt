package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 16.07.18.
 * View for Splash activity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface SplashView: MvpView {
    fun setupConfiguration(configuration: Configuration, miner: Miner)
}