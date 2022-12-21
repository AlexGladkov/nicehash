package com.dev.nicehash.app.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dev.nicehash.app.views.DetailView
import com.github.mikephil.charting.data.Entry

/**
 * Created by Alex Gladkov on 30.07.18.
 * Presenter for Detail Activity
 */
@InjectViewState
class DetailPresenter: MvpPresenter<DetailView>() {

    fun onChartTimeClick(position: Int) {
        viewState.setupChartView(position = position)
        loadChartData(position = position)
    }

    private fun loadChartData(position: Int) {
        val emptyData = ArrayList<Entry>()

        when (position) {
            0 -> {
                emptyData.add(Entry(0f, 0.0001f))
                emptyData.add(Entry(1f, 0.0007f))
                emptyData.add(Entry(2f, 0.0005f))
                emptyData.add(Entry(3f, 0.0010f))
                emptyData.add(Entry(4f, 0.0006f))
                emptyData.add(Entry(5f, 0.0007f))
                emptyData.add(Entry(6f, 0.0011f))
            }

            3 -> {
                emptyData.add(Entry(0f, 0.0022f))
                emptyData.add(Entry(1f, 0.0007f))
                emptyData.add(Entry(2f, 0.0012f))
                emptyData.add(Entry(3f, 0.0015f))
                emptyData.add(Entry(4f, 0.0008f))
                emptyData.add(Entry(5f, 0.0001f))
                emptyData.add(Entry(6f, 0.0020f))
            }

            else -> {
                emptyData.add(Entry(0f, 0.0011f))
                emptyData.add(Entry(1f, 0.0004f))
                emptyData.add(Entry(2f, 0.0009f))
                emptyData.add(Entry(3f, 0.0060f))
                emptyData.add(Entry(4f, 0.0052f))
                emptyData.add(Entry(5f, 0.0023f))
                emptyData.add(Entry(6f, 0.0042f))
            }
        }

        viewState.setupChartData(data = emptyData)
    }
}