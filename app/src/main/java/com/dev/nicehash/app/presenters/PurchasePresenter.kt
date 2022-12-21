package com.dev.nicehash.app.presenters

import com.anjlab.android.iab.v3.BillingProcessor
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.views.PurchaseView

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

