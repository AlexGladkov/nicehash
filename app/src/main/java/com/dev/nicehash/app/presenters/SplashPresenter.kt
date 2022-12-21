package com.dev.nicehash.app.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dev.nicehash.app.views.SplashView
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alex Gladkov on 16.07.18.
 * Presenter for Splash Activity
 */
@InjectViewState
class SplashPresenter(val configurationRepository: ConfigurationRepository,
                      val minerRepository: MinerRepository): MvpPresenter<SplashView>() {
    private val TAG = SplashPresenter::class.java.simpleName

    fun fetchConfiguration() {
        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configuration ->
                    minerRepository.loadMinersFromDb(minerId = configuration.minerId)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ miners ->
                                viewState.setupConfiguration(configuration = configuration,
                                        miner = miners.firstOrNull { it.id == configuration.minerId } ?: miners[0])
                            }, { error ->
                                error.printStackTrace()
                            })
                }, { error ->
                    error.printStackTrace()
                    viewState.setupConfiguration(configuration = Configuration.defaultInstance(),
                            miner = Miner.getDefaultInstance())
                })
    }
}