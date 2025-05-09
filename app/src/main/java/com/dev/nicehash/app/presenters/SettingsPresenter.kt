package com.dev.nicehash.app.presenters

import android.annotation.SuppressLint
import com.dev.nicehash.app.views.SettingsView
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by Alex Gladkov on 24.06.18.
 * Presenter for settings activity
 */
@InjectViewState
class SettingsPresenter(val router: Router, private val configurationRepository: ConfigurationRepository): MvpPresenter<SettingsView>() {

    fun performClick(id: Int) {
//        when (id) {
//            0 -> router.replaceScreen(ScreenKeys.Notifications.value)
//            1 -> router.replaceScreen(ScreenKeys.Themes.value)
//            2 -> router.replaceScreen(ScreenKeys.Tab.value)
//            3 -> router.replaceScreen(ScreenKeys.Language.value)
//            4 -> router.replaceScreen(ScreenKeys.Currencies.value)
//            5 -> router.replaceScreen(ScreenKeys.Service.value)
//            6 -> router.replaceScreen(ScreenKeys.Purchases.value)
//            7 -> router.replaceScreen(ScreenKeys.About.value)
//        }
    }

    @SuppressLint("CheckResult")
    fun fetchConfiguration() {
        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configuration ->
                    viewState.setupConfiguration(configuration = configuration)
                }, { error ->
                    error.printStackTrace()
                    viewState.setupConfiguration(configuration = Configuration.defaultInstance())
                })
    }

    @SuppressLint("CheckResult")
    fun updateConfiguration(configuration: Configuration) {
        configurationRepository.updateConfiguration(configuration = configuration)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { })
    }

    fun onBackClick() {
        router.exit()
    }
}