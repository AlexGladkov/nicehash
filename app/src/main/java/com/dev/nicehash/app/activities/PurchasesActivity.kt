package com.dev.nicehash.app.activities

import android.os.Bundle
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.presenters.PurchasePresenter
import com.dev.nicehash.app.views.PurchaseView
import com.dev.nicehash.base.BaseActivity
import moxy.presenter.InjectPresenter

/**
 * Created by Alex Gladkov on 24.06.18.
 * Activity for IAP
 */
class PurchasesActivity: BaseActivity(), PurchaseView {

    // MARK: - Presenter setup
    @InjectPresenter
    lateinit var purchasePresenter: PurchasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@PurchasesActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchases)

//        imgPurchasesBack.setOnClickListener { onBackPressed() }
//
//        btnPurchaseThemes.setOnClickListener { purchasePresenter.performBuy(index = 0) }
//        btnPurchaseAddMiner.setOnClickListener { purchasePresenter.performBuy(index = 1) }
//        btnPurchaseRemoveAds.setOnClickListener { purchasePresenter.performBuy(index = 2) }
//        btnPurchaseSilver.setOnClickListener { purchasePresenter.performBuy(index = 3) }
//        btnPurchaseGold.setOnClickListener { purchasePresenter.performBuy(index = 4) }

        purchasePresenter.checkAvailability(isAvailable = BillingProcessor.isIabServiceAvailable(applicationContext))
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    // MARK: - View implementation
    override fun showError(message: Int) {
        Toast.makeText(applicationContext, getString(message), Toast.LENGTH_LONG).show()
    }

    override fun makeTestBuy() {

    }
}