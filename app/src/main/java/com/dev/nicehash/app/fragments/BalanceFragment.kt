package com.dev.nicehash.app.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.BalanceAdapter
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.BalancePresenter
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.app.views.BalanceView
import com.dev.nicehash.app.views.MainView
import com.dev.nicehash.base.BaseAdapterCallback
import com.dev.nicehash.base.BaseContainer
import com.dev.nicehash.domain.models.Balance
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.domain.repositories.MoneyRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.ScreenKeys
import com.dev.nicehash.helpers.ListConfig
import com.github.terrakok.cicerone.Navigator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 27.06.18.
 * Fragment for balance tab
 */
class BalanceFragment: BaseContainer(), BalanceView {

    // MARK: - Injects
    @Inject lateinit var moneyRepository: MoneyRepository
    @Inject lateinit var minerRepository: MinerRepository
    @Inject lateinit var exchangeServer: ExchangeServer

    // MARK: - Presenter setup
    @InjectPresenter
    lateinit var balancePresenter: BalancePresenter
    @ProvidePresenter
    fun provideBalancePresenter(): BalancePresenter {
        return BalancePresenter(minerRepository = minerRepository, exchangeServer = exchangeServer,
                currency = ExchangeServerImpl.ExchangeCode.Ru, moneyRepository = moneyRepository)
    }

    // MARK: - UI Setup
    private val mAdapter = BalanceAdapter()

    companion object {
        fun getNewInstance(name: String, miner: String, minerID: Int): BalanceFragment {
            val fragment = BalanceFragment()
            val args = Bundle()
            args.putString(Keys.Name.value, name)
            args.putString(Keys.Miner.value, miner)
            args.putInt(Keys.MinerID.value, minerID)

            fragment.arguments = args
            return fragment
        }
    }

    private var navigator: Navigator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(fragment = this@BalanceFragment)
        super.onCreate(savedInstanceState)
        mAdapter.attachCallback(object: BaseAdapterCallback<Balance> {
            override fun onItemClick(model: Balance, view: View) {
//                (activity as? RouterProvider)?.getRouter()?.replaceScreen(ScreenKeys.Detail.value, model)
            }

            override fun onLongClick(model: Balance, view: View): Boolean {
                return true
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val listConfig = ListConfig.Builder(mAdapter)
                    .setHasFixedSize(true)
                    .setHasNestedScroll(true)
                    .build(it)
//            listConfig.applyConfig(it, recyclerBalance)
        }

        balancePresenter.fetchUnpaid(miner = arguments?.getString(Keys.Miner.value).orEmpty(),
                id = arguments?.getInt(Keys.MinerID.value) ?: -1)
        balancePresenter.fetchBalance(miner = arguments?.getString(Keys.Miner.value).orEmpty(),
                id = arguments?.getInt(Keys.MinerID.value) ?: -1,
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
    override fun setupBalance(data: List<Balance>) {
        if (mAdapter.hasItems) {
            mAdapter.updateItems(itemsList = data)
        } else {
            mAdapter.setList(dataList = data)
        }

//        recyclerBalance.visibility = View.VISIBLE
//        txtBalanceNoItems.visibility = View.GONE
    }

    override fun setupBarItems(unpaid: String, usd: String) {
        (activity as? MainView)?.displayNavigationInfo(leftValue = unpaid, rightValue = usd)
    }

    override fun startLoading() {
//        cpvBalance.visibility = View.VISIBLE
//        recyclerBalance.visibility = View.GONE
//        txtBalanceNoItems.visibility = View.GONE
    }

    override fun endLoading() {
//        cpvBalance.visibility = View.GONE
    }

    override fun showNoItems() {
//        recyclerBalance.visibility = View.GONE
//        txtBalanceNoItems.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}