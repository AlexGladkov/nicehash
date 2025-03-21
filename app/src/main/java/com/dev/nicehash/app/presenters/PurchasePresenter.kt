package com.dev.nicehash.app.presenters

import com.dev.nicehash.R
import com.dev.nicehash.app.views.PurchaseView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PurchasePresenter: MvpPresenter<PurchaseView>() {

    fun performBuy(index: Int) {
        viewState.makeTestBuy()
    }

    fun checkAvailability(isAvailable: Boolean) {
        if (!isAvailable) {
            viewState.showError(message = R.string.purchase_unavailable)
        }
    }
}

