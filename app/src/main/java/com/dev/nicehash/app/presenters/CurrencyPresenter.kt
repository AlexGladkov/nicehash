package com.dev.nicehash.app.presenters

import android.annotation.SuppressLint
import android.util.Log
import com.dev.nicehash.R
import com.dev.nicehash.app.views.CurrencyView
import com.dev.nicehash.domain.models.Currency
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.helpers.EnumCollections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.LinkedList

/**
 * Created by Alex Gladkov on 25.07.18.
 * Presenter for currency activity (domain level)
 * @see .app.activities.CurrencyActivity
 */
@InjectViewState
class CurrencyPresenter(val configurationRepository: ConfigurationRepository): MvpPresenter<CurrencyView>() {
    private val TAG = CurrencyPresenter::class.java.simpleName

    fun fetchCurrencies() {
        val data = LinkedList<Currency>()

        data.add(Currency(id = EnumCollections.Currency.Russian.id,
                title = EnumCollections.Currency.Russian.titleRes, header = R.string.currency_base,
                btc = "413.558,23", imgRes = EnumCollections.Currency.Russian.flagRes, prototype = EnumCollections.Currency.Russian))

        data.add(Currency(id = EnumCollections.Currency.Euro.id, title = EnumCollections.Currency.Euro.titleRes,
                header = R.string.currency_popular, btc = "5,664", imgRes = EnumCollections.Currency.Euro.flagRes,
                prototype = EnumCollections.Currency.Euro))
        data.add(Currency(id = EnumCollections.Currency.BritainPound.id, imgRes = EnumCollections.Currency.BritainPound.flagRes,
                title = EnumCollections.Currency.BritainPound.titleRes, header = -1, btc = "5,104",
                prototype = EnumCollections.Currency.BritainPound))
        data.add(Currency(id = EnumCollections.Currency.USD.id, imgRes = EnumCollections.Currency.USD.flagRes,
                title = EnumCollections.Currency.USD.titleRes, header = -1, btc = "7,188",
                prototype = EnumCollections.Currency.USD))

        data.add(Currency(id = EnumCollections.Currency.Australian.id, title = EnumCollections.Currency.Australian.titleRes,
                header = R.string.currency_all, btc = "9,344", imgRes = EnumCollections.Currency.Australian.flagRes,
                prototype = EnumCollections.Currency.Australian))
        data.add(Currency(id = EnumCollections.Currency.Belgium.id, title = EnumCollections.Currency.Belgium.titleRes,
                header = -1, btc = "11,404", imgRes = EnumCollections.Currency.Belgium.flagRes,
                prototype = EnumCollections.Currency.Belgium))
        data.add(Currency(id = EnumCollections.Currency.Canada.id, title = EnumCollections.Currency.Canada.titleRes,
                header = -1, btc = "9,273", imgRes = EnumCollections.Currency.Canada.flagRes,
                prototype = EnumCollections.Currency.Canada))
        data.add(Currency(id = EnumCollections.Currency.CHF.id, title = EnumCollections.Currency.CHF.titleRes,
                header = -1, btc = "6,874", imgRes = EnumCollections.Currency.CHF.flagRes,
                prototype = EnumCollections.Currency.CHF))
        data.add(Currency(id = EnumCollections.Currency.China.id, title = EnumCollections.Currency.China.titleRes,
                header = -1, btc = "45,273", imgRes = EnumCollections.Currency.China.flagRes,
                prototype = EnumCollections.Currency.China))
        data.add(Currency(id = EnumCollections.Currency.Czech.id, title = EnumCollections.Currency.Czech.titleRes,
                header = -1, btc = "148,404", imgRes = EnumCollections.Currency.Czech.flagRes,
                prototype = EnumCollections.Currency.Czech))
        data.add(Currency(id = EnumCollections.Currency.Dania.id, title = EnumCollections.Currency.Dania.titleRes,
                header = -1, btc = "43,480.21", imgRes = EnumCollections.Currency.Dania.flagRes,
                prototype = EnumCollections.Currency.Dania))

        viewState.setupView(data = data)
    }

    @SuppressLint("CheckResult")
    fun setCurrency(currency: Currency) {
        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { config ->
                    config.currency = currency.prototype
                    configurationRepository.updateConfiguration(configuration = config)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Log.e(TAG, "configuration updated")
                            }, { error ->
                                Log.e(TAG, "error updating ${error.localizedMessage}")
                            })
                }
    }
}