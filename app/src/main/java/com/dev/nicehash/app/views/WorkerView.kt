package com.dev.nicehash.app.views

import com.dev.nicehash.domain.models.WorkerHub
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Alex Gladkov on 15.07.18.
 * View for worker tab
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface WorkerView: MvpView {
    fun startLoading()
    fun endLoading()
    fun setupWorkers(data: List<WorkerHub>)
    fun setupNoItems()
    fun showError(message: String)
}