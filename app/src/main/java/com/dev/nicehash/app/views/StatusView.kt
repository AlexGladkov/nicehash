package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dev.nicehash.domain.models.Status

/**
 * Created by Alex Gladkov on 25.07.18.
 * View for status activity
 * @see .app.activities.ServiceStatusActivity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface StatusView: MvpView {
    fun setupView(data: List<Status>)
}