package com.dev.nicehash.app.presenters

import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.views.MainView
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.ScreenKeys
import com.github.mikephil.charting.data.Entry
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

/**
 * Created by Alex Gladkov on 21.06.18.
 * Presenter for MainActivity
 */
@InjectViewState
class MainPresenter(val router: Router, private val minerRepository: MinerRepository): MvpPresenter<MainView>() {
    private val TAG = MainPresenter::class.java.simpleName
    private var chartHeight = 0f

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        minerRepository.fetchMinerStatistics()
    }

    fun onDeviceClick() {
//        router.replaceScreen(ScreenKeys.Choose.value)
    }

    fun onTabClick(position: Int) {
        viewState.setupNavigation(position = position)
//        when (position) {
//            0 -> router.replaceScreen(ScreenKeys.Income.value)
//            1 -> router.replaceScreen(ScreenKeys.Balance.value)
//            2 -> router.replaceScreen(ScreenKeys.Payouts.value)
//            3 -> router.replaceScreen(ScreenKeys.Workers.value)
//        }
    }

    fun measureChartHeight(screenHeight: Float, bottomBlockHeight: Float,
                           topBlockHeight: Float) {
        chartHeight = screenHeight - bottomBlockHeight - topBlockHeight
        viewState.setupChartHeight(value = chartHeight)
    }

    fun handleConfiguration(extras: Bundle?) {
        (extras?.get(Keys.Configuration.value) as? Configuration)?.let {
            viewState.setupConfiguration(configuration = it)
        }
    }

    fun handleScrolling(scrollY: Int, daysBarHeight: Float, introBarHeight: Float) {
        // MARK: - Setup intro bar position
        if (scrollY <= chartHeight + daysBarHeight) {
            viewState.setupIntroBar(introBarPosition = scrollY.toFloat())
        } else {
            viewState.setupIntroBar(introBarPosition = chartHeight + daysBarHeight)
            // MARK: - Setup tab bar position
            if (scrollY > chartHeight + daysBarHeight + introBarHeight) {
                viewState.setupTabBar(tabBarPosition = scrollY - chartHeight - daysBarHeight - introBarHeight)
            } else {
                viewState.setupTabBar(tabBarPosition = 0f)
            }
        }
    }

    fun onChartTimeClick(position: Int, id: Int, miner: String) {
        viewState.setupChartView(position = position)
        loadChartData(position = position, id = id, miner = miner)
    }

    private fun loadChartData(position: Int, id: Int, miner: String) {
        val emptyData = ArrayList<Entry>()
        var time = (DateTime().minusMinutes(70).millis / 1000)
        var elapsedHours = 1

        when (position) {
            1 -> {
                elapsedHours = 6
                time = (DateTime().minusHours(6).millis / 1000)
            }
            2 -> {
                elapsedHours = 12
                time = (DateTime().minusHours(12).millis / 1000)
            }
            3 -> {
                elapsedHours = 24
                time = (DateTime().minusDays(1).millis / 1000)
            }
            4 -> {
                elapsedHours = 72
                time = (DateTime().minusDays(3).millis / 1000)
            }
            5 -> {
                elapsedHours = 24 * 7
                time = (DateTime().minusDays(7).millis / 1000)
            }
        }

        minerRepository.fetchMinerChart(miner = miner, time = time, elapsedHours = elapsedHours)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isNotEmpty()) {
                        response.forEach {
                            emptyData.add(Entry(response.indexOf(it).toFloat(), it.y.toFloat()))
                        }

                        viewState.setupChartData(data = emptyData)
                    } else {
                        viewState.showError(message = R.string.chart_no_data)
                    }
                }, { error ->
                    viewState.showError(message = error.localizedMessage)
                })
    }
}