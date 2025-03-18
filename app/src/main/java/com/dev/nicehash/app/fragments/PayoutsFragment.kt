package com.dev.nicehash.app.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.PayoutAdapter
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.PayoutsPresenter
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.views.MainView
import com.dev.nicehash.app.views.PayoutsView
import com.dev.nicehash.base.BaseContainer
import com.dev.nicehash.domain.models.Payout
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.helpers.ListConfig
import com.github.terrakok.cicerone.Navigator
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 27.06.18.
 * Fragment for payouts tab
 */
class PayoutsFragment: BaseContainer(), PayoutsView {
    private val TAG = PayoutsFragment::class.java.simpleName

    // MARK: - Injects
    @Inject lateinit var minerRepository: MinerRepository
    @Inject lateinit var exchangeServer: ExchangeServer

    // MARK: - Presenter setup
    @InjectPresenter lateinit var payoutsPresenter: PayoutsPresenter
    @ProvidePresenter
    fun providePresenter(): PayoutsPresenter {
        return PayoutsPresenter(minerRepository = minerRepository, exchangeServer = exchangeServer)
    }

    // MARK: - UI Setup
    private val mAdapter = PayoutAdapter()

    companion object {
        fun getNewInstance(name: String, miner: String, minerID: Int): PayoutsFragment {
            val fragment = PayoutsFragment()
            val args = Bundle()
            args.putString(Keys.Name.value, name)
            args.putString(Keys.Miner.value, miner)
            args.putInt(Keys.MinerID.value, minerID)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(fragment = this@PayoutsFragment)
        super.onCreate(savedInstanceState)
    }

    private var navigator: Navigator? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val listConfig = ListConfig.Builder(mAdapter)
                    .setHasFixedSize(true)
                    .setHasNestedScroll(true)
                    .build(it)
//            listConfig.applyConfig(it, recyclerPayouts)
        }

        payoutsPresenter.fetchPayouts(miner = arguments?.getString(Keys.Miner.value).orEmpty(),
                id = arguments?.getInt(Keys.MinerID.value) ?: 0,
                isRefresh = mAdapter.getItems().isEmpty())
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
    override fun setupPayouts(data: List<Payout>) {
        if (mAdapter.hasItems) {
            mAdapter.updateItems(itemsList = data)
        } else {
            mAdapter.setList(dataList = data)
        }

//        recyclerPayouts.visibility = View.VISIBLE
//        txtPayoutsNoItems.visibility = View.GONE
    }

    override fun setupBarInfo(paidBTC: Double, usd: Float) {
        (activity as? MainView)?.displayNavigationInfo(leftValue = DecimalFormat("##.######")
                .format(paidBTC), rightValue = DecimalFormat("##.##").format(usd))
    }

    override fun setupNoItems() {
//        txtPayoutsNoItems.visibility = View.VISIBLE
//        recyclerPayouts.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun startLoading() {
//        cpvPayouts.visibility = View.VISIBLE
//        recyclerPayouts.visibility = View.GONE
//        txtPayoutsNoItems.visibility = View.GONE
    }

    override fun endLoading() {
//        cpvPayouts.visibility = View.GONE
    }
}