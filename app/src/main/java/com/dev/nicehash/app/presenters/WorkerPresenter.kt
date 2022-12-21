package com.dev.nicehash.app.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dev.nicehash.app.views.WorkerView
import com.dev.nicehash.domain.models.WorkerHub
import com.dev.nicehash.domain.repositories.MinerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alex Gladkov on 15.07.18.
 * View for worker tab
 */
@InjectViewState
class WorkerPresenter(val minerRepository: MinerRepository) : MvpPresenter<WorkerView>() {

    fun fetchWorkers(miner: String, id: Int, isRefresh: Boolean) {
        if (id < 0) {
            val array = ArrayList<WorkerHub>()
            val subArray = ArrayList<WorkerHub.Worker>()
            subArray.add(WorkerHub.Worker(title = "DaggerHashimoto", difference = 0.25f,
                    upDynamic = 33.03f, downDynamic = 0f, min = 68))
            subArray.add(WorkerHub.Worker(title = "Pascal", difference = 0.2f,
                    upDynamic = 0.34f, downDynamic = 0.04f, min = 46))
            array.add(WorkerHub(title = "Top", countryCode = "EU", workerTitles = subArray))
            array.add(WorkerHub(title = "Bottom", countryCode = "US", workerTitles = subArray))
            viewState.setupWorkers(data = array)
        } else {
            if (isRefresh) viewState.startLoading()
            minerRepository.fetchWorkers(miner = miner)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        viewState.endLoading()
                        if (response.isEmpty()) {
                            viewState.setupNoItems()
                        } else {
                            viewState.setupWorkers(data = response)
                        }
                    }, { error ->
                        viewState.endLoading()
                        viewState.showError(message = error.localizedMessage)
                    })
        }
    }
}