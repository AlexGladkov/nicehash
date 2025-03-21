package com.dev.nicehash.app.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 21.07.18.
 * View for Themes Activity
 */
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ThemesView: MvpView {
    fun setupTab(position: Int)
    fun switchTheme(isLight: Boolean, color: Int)
    fun setupView(isLight: Boolean, color: Int)
}