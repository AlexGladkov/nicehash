package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dev.nicehash.domain.models.WorkerHub

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