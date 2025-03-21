package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 23.06.18.
 * View interface for ChooseActivity
 */
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ChooseView: MvpView {
    fun setupMiners(list: List<Miner>)
    fun setupConfiguration(configuration: Configuration)
}