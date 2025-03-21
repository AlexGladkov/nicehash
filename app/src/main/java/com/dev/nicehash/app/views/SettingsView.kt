package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Configuration
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 24.06.18.
 * View interface for Settings Activity
 */
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SettingsView: MvpView {
    fun setupConfiguration(configuration: Configuration)
}