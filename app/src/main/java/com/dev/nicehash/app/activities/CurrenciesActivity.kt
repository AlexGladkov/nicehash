package com.dev.nicehash.app.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.CurrencyAdapter
import com.dev.nicehash.app.presenters.CurrencyPresenter
import com.dev.nicehash.app.views.CurrencyView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.base.BaseAdapterCallback
import com.dev.nicehash.domain.models.Currency
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.helpers.EnumCollections
import com.dev.nicehash.helpers.ListConfig
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 24.06.18.
 * Activity for currencies settings
 */
class CurrenciesActivity: BaseActivity(), CurrencyView {
    private val TAG = CurrenciesActivity::class.java.simpleName
    private val mAdapter = CurrencyAdapter()

    @Inject lateinit var configurationRepository: ConfigurationRepository

    @InjectPresenter
    lateinit var currencyPresenter: CurrencyPresenter
    @ProvidePresenter
    fun providePresenter(): CurrencyPresenter {
        return CurrencyPresenter(configurationRepository = configurationRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@CurrenciesActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)

//        imgCurrenciesBack.setOnClickListener { onBackPressed() }

        val listConfig = ListConfig.Builder(adapter = mAdapter)
                .setHasNestedScroll(isNestedScroll = false)
                .setHasFixedSize(isFixedSize = true)
                .build(context = applicationContext)

//        listConfig.applyConfig(context = applicationContext, recyclerView = recyclerCurrency)
        currencyPresenter.fetchCurrencies()

        mAdapter.attachCallback(object: BaseAdapterCallback<Currency> {
            override fun onItemClick(model: Currency, view: View) {
                if (!mAdapter.checkSameFirstPosition(model = model)) {
                    if (!mAdapter.checkIsPopular(model = model)) {
                        mAdapter.updateItem(position = 0, newItem = Currency(id = model.id,
                                imgRes = model.imgRes, title = model.title, btc = model.btc, header = R.string.currency_base,
                                prototype = model.prototype))
                    } else {
                        mAdapter.performPopularClicked(model = model)
                    }
                }

                currencyPresenter.setCurrency(currency = model)
            }

            override fun onLongClick(model: Currency, view: View): Boolean {
                return true
            }
        })
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    // MARK: - View implementation
    override fun setupView(data: List<Currency>) {
        if (mAdapter.hasItems) {
            mAdapter.updateItems(itemsList = data)
        } else {
            mAdapter.setList(dataList = data)
        }
    }
}