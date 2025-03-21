package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.Status
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 25.07.18.
 * View for status activity
 * @see .app.activities.ServiceStatusActivity
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface StatusView: MvpView {
    fun setupView(data: List<Status>)
}