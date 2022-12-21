package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner

/**
 * Created by Alex Gladkov on 16.07.18.
 * View for Splash activity
 */
interface SplashView: MvpView {
    fun setupConfiguration(configuration: Configuration, miner: Miner)
}