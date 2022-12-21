package com.dev.nicehash.app.views

import com.arellomobile.mvp.MvpView
import com.dev.nicehash.domain.models.Configuration

/**
 * Created by Alex Gladkov on 24.06.18.
 * View interface for Settings Activity
 */
interface SettingsView: MvpView {
    fun setupConfiguration(configuration: Configuration)
}