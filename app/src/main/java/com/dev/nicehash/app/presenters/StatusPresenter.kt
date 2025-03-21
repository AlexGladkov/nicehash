package com.dev.nicehash.app.presenters

import com.dev.nicehash.app.views.StatusView
import com.dev.nicehash.domain.models.Status
import com.dev.nicehash.helpers.EnumCollections
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by Alex Gladkov on 25.07.18.
 * Presenter for service status activity
 * @see .app.activities.ServiceStatusActivity
 */
@InjectViewState
class StatusPresenter: MvpPresenter<StatusView>() {

    fun fetchStatuses() {
        val data: MutableList<Status> = ArrayList()
        data.add(Status(id = 0, header = " ", title = "Miner statistics", status = EnumCollections.Status.Green))
        data.add(Status(id = 1, header = "", title = "Nicehash miner endpoint", status = EnumCollections.Status.Green))
        data.add(Status(id = 2, header = "", title = "api.nicehash.com", status = EnumCollections.Status.Green))
        data.add(Status(id = 3, header = "", title = "www.nicehash.com", status = EnumCollections.Status.Yellow))

        data.add(Status(id = 0, header = "Servers", title = "Brazil", status = EnumCollections.Status.Green))
        data.add(Status(id = 1, header = "", title = "China", status = EnumCollections.Status.Green))
        data.add(Status(id = 2, header = "", title = "Japan", status = EnumCollections.Status.Red))
        data.add(Status(id = 3, header = "", title = "Europe", status = EnumCollections.Status.Green))
        viewState.setupView(data = data)
    }
}