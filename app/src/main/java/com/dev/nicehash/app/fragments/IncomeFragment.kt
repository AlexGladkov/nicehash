package com.dev.nicehash.app.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.IncomePresenter
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.app.views.IncomeView
import com.dev.nicehash.app.views.MainView
import com.dev.nicehash.base.BaseContainer
import com.dev.nicehash.domain.models.Income
import com.dev.nicehash.domain.repositories.CurrencyRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.domain.repositories.MoneyRepository
import com.dev.nicehash.enums.Keys
import com.github.terrakok.cicerone.Navigator
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 27.06.18.
 * Fragment for income tab
 */
class IncomeFragment: BaseContainer(), IncomeView {

    // MARK: - Presenter setup
    @InjectPresenter lateinit var incomePresenter: IncomePresenter
    @ProvidePresenter
    fun provideIncomePresenter(): IncomePresenter {
        return IncomePresenter(exchangeServer = exchangeServer,
                repository = moneyRepository)
    }

    // MARK: - Injects
    @Inject lateinit var exchangeServer: ExchangeServer
    @Inject lateinit var moneyRepository: MoneyRepository

    companion object {
        fun getNewInstance(name: String, miner: String, minerID: Int): IncomeFragment {
            val fragment = IncomeFragment()
            val args = Bundle()
            args.putString(Keys.Name.value, name)
            args.putString(Keys.Miner.value, miner)
            args.putInt(Keys.MinerID.value, minerID)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(fragment = this@IncomeFragment)
        super.onCreate(savedInstanceState)
    }

    private var navigator: Navigator? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_income, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incomePresenter.fetchIncome(currency = ExchangeServerImpl.ExchangeCode.Ru,
                miner = arguments?.getString(Keys.Miner.value).orEmpty())
    }

    override fun getNavigator(): Navigator? {
//        return if (navigator == null) {
//            navigator = object: SupportAppNavigator(activity, childFragmentManager, R.id.container) {
//                override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
//                    return when (screenKey) {
//                        else -> null
//                    }
//                }
//
//                override fun createFragment(screenKey: String, data: Any?): Fragment? {
//                    return when (screenKey) {
//                        else -> null
//                    }
//                }
//
//                override fun exit() {
//                    super.exit()
//                    activity?.let { (it as RouterProvider).getRouter().exit() }
//                }
//            }
//
//            navigator as SupportAppNavigator
//        } else {
//            navigator!!
//        }

        return null
    }

    // MARK: - Base implementation
    override fun doubleTap() {

    }

    override fun updateContainer() {

    }

    // MARK: - View implementation
    @SuppressLint("SetTextI18n")
    override fun setupIncome(income: Income, currencyRate: Float, usdRate: Float) {
//        txtIncomeHourValue.text = DecimalFormat("##.##").format(income.hourValue)
//        txtIncomeHourBtc.text = DecimalFormat("##.########").format(income.hourBtc)
//
//        txtIncomeDayValue.text = DecimalFormat("##.##").format(income.dayValue)
//        txtIncomeDayBtc.text = DecimalFormat("##.########").format(income.dayBtc)
//
//        txtIncomeWeekValue.text = DecimalFormat("##.##").format(income.weekValue)
//        txtIncomeWeekBtc.text = DecimalFormat("##.########").format(income.weekBtc)
//
//        txtIncomeMonthValue.text = DecimalFormat("##.##").format(income.monthValue)
//        txtIncomeMonthBtc.text = DecimalFormat("##.########").format(income.monthBtc)
//
//        txtIncomeBtcUsd.text = " ${DecimalFormat("##.##").format(usdRate)}"
//        txtIncomeCurrency.text = "${getString(R.string.currency_btc)}/${getString(R.string.currency_rub)} = "
//        txtIncomeCurrencyValue.text = DecimalFormat("##.##").format(currencyRate)

        (activity as? MainView)?.displayNavigationInfo(leftValue = DecimalFormat("##.######").format(income.dayBtc),
                rightValue = DecimalFormat("##.##").format(income.dayValue))
    }
}