package com.dev.nicehash.app.presenters

import android.annotation.SuppressLint
import com.dev.nicehash.app.App
import com.dev.nicehash.app.views.ThemesView
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.enums.Theme
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by Alex Gladkov on 21.07.18.
 * Presenters for themes activity
 */
@InjectViewState
class ThemesPresenter(val configurationRepository: ConfigurationRepository): MvpPresenter<ThemesView>() {
    private var isLight = false
    private var color = 0

    fun onTabClick(position: Int) {
        viewState.setupTab(position = position)
    }

    fun setColor(color: Int) {
        this.color = color
    }

    fun setLight(isLight: Boolean) {
        this.isLight = isLight
    }

    fun setupTheme(isLight: Boolean) {
        App.isMainNeedsToReload = true
        App.isChooseNeedsToReload = true
        App.isSettingsNeedsToReload = true
        viewState.switchTheme(isLight = isLight, color = color)
    }

    fun setupView() {
        saveSettings()
        viewState.setupView(isLight = isLight, color = color)
    }

    fun onPaletteClick(position: Int) {
        App.isMainNeedsToReload = true
        App.isChooseNeedsToReload = true
        App.isSettingsNeedsToReload = true
        viewState.switchTheme(isLight = isLight, color = position)
    }

    @SuppressLint("CheckResult")
    private fun saveSettings() {
        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ config ->
                    if (isLight) {
                        when (color) {
                            0 -> {
                                App.theme = Theme.LightBlue
                                config.theme = Theme.LightBlue
                            }
                            1 -> {
                                App.theme = Theme.LightGreen
                                config.theme = Theme.LightGreen
                            }
                            2 -> {
                                App.theme = Theme.LightOrange
                                config.theme = Theme.LightOrange
                            }
                            3 -> {
                                App.theme = Theme.LightRed
                                config.theme = Theme.LightRed
                            }
                            4 -> {
                                App.theme = Theme.LightPurple
                                config.theme = Theme.LightPurple
                            }
                        }
                    } else {
                        when (color) {
                            0 -> {
                                App.theme = Theme.DarkBlue
                                config.theme = Theme.DarkBlue
                            }
                            1 -> {
                                App.theme = Theme.DarkGreen
                                config.theme = Theme.DarkGreen
                            }
                            2 -> {
                                App.theme = Theme.DarkOrange
                                config.theme = Theme.DarkOrange
                            }
                            3 -> {
                                App.theme = Theme.DarkRed
                                config.theme = Theme.DarkRed
                            }
                            4 -> {
                                App.theme = Theme.DarkPurple
                                config.theme = Theme.DarkPurple
                            }
                        }
                    }

                    configurationRepository.updateConfiguration(configuration = config)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ }, { })
                })
    }

    fun setupAppTheme() {
        isLight = !App.isDark
        color = App.theme.color
        when (App.theme) {
            Theme.DarkOrange -> viewState.setupView(isLight = false, color = 2)
            Theme.LightOrange -> viewState.setupView(isLight = true, color = 2)
            Theme.DarkBlue -> viewState.setupView(isLight = false, color = 0)
            Theme.LightBlue -> viewState.setupView(isLight = true, color = 0)
            Theme.DarkGreen -> viewState.setupView(isLight = false, color = 1)
            Theme.LightGreen -> viewState.setupView(isLight = true, color = 1)
            Theme.DarkRed -> viewState.setupView(isLight = false, color = 3)
            Theme.LightRed -> viewState.setupView(isLight = true, color = 3)
            Theme.DarkPurple -> viewState.setupView(isLight = false, color = 4)
            Theme.LightPurple -> viewState.setupView(isLight = true, color = 4)
        }
    }
}