package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner

/**
 * Created by Alex Gladkov on 23.06.18.
 * View interface for ChooseActivity
 */
interface ChooseView: MvpView {
    fun setupMiners(list: List<Miner>)
    fun setupConfiguration(configuration: Configuration)
}