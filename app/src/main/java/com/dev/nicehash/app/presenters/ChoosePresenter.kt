package com.dev.nicehash.app.presenters

import android.os.Bundle
import com.dev.nicehash.app.views.ChooseView
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.ScreenKeys
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by Alex Gladkov on 23.06.18.
 * Presenter for ChooseActivity
 */
@InjectViewState
class ChoosePresenter(val router: Router, val minerRepository: MinerRepository,
                      val configurationRepository: ConfigurationRepository): MvpPresenter<ChooseView>() {
    private val TAG = ChoosePresenter::class.java.simpleName

    fun loadMiners() {
        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configuration ->
                    minerRepository.loadMinersFromDb(minerId = configuration.minerId)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ result ->
                                viewState.setupMiners(list = result)
                            })
                }, { error ->

                })
    }

    fun onMinerClick(miner: Miner) {
        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configuration ->
                    configuration.minerId = miner.id
                    configurationRepository.updateConfiguration(configuration = configuration)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
//                                router.replaceScreen(ScreenKeys.Splash.value)
                            }, { error ->

                            })
                }, { error ->

                })
    }

    fun onGearClick() {
//        router.replaceScreen(ScreenKeys.Settings.value)
    }

    fun onAddClick() {
//        router.replaceScreen(ScreenKeys.Add.value)
    }

    fun editClick(miner: Miner) {
        val bundle = Bundle()
        bundle.putParcelable(Keys.Miner.value, miner)
//        router.replaceScreen(ScreenKeys.Edit.value, bundle)
    }

    fun deleteClick(miner: Miner) {

    }

    fun handleConfiguration(extras: Bundle?) {
        (extras?.get(Keys.Configuration.value) as? Configuration)?.let {
            viewState.setupConfiguration(configuration = it)
        }
    }
}