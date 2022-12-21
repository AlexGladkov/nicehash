package com.dev.nicehash.app.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.WorkerHubAdapter
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.WorkerPresenter
import com.dev.nicehash.app.views.MainView
import com.dev.nicehash.app.views.WorkerView
import com.dev.nicehash.base.BaseContainer
import com.dev.nicehash.domain.models.WorkerHub
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.helpers.ListConfig
import kotlinx.android.synthetic.main.fragment_workers.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 27.06.18.
 * Fragment for workers tab
 */
class WorkersFragment: BaseContainer(), WorkerView {

    // MARK: - Injects
    @Inject lateinit var minerRepository: MinerRepository

    // MARK: - Presenter setup
    @InjectPresenter lateinit var workersPresenter: WorkerPresenter
    @ProvidePresenter
    fun provideWorkerPresenter(): WorkerPresenter {
        return WorkerPresenter(minerRepository = minerRepository)
    }

    // MARK: - UI Setup
    private val mAdapter = WorkerHubAdapter()

    companion object {
        fun getNewInstance(name: String, miner: String, minerID: Int): WorkersFragment {
            val fragment = WorkersFragment()
            val args = Bundle()
            args.putString(Keys.Name.value, name)
            args.putString(Keys.Miner.value, miner)
            args.putInt(Keys.MinerID.value, minerID)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(fragment = this@WorkersFragment)
        super.onCreate(savedInstanceState)
    }

    private var navigator: Navigator? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val listConfig = ListConfig.Builder(mAdapter)
                    .setHasFixedSize(true)
                    .setHasNestedScroll(true)
                    .build(it)
            listConfig.applyConfig(it, recyclerWorkers)
        }

        workersPresenter.fetchWorkers(miner = arguments?.getString(Keys.Miner.value).orEmpty(),
                id = arguments?.getInt(Keys.MinerID.value) ?: -1,
                isRefresh = mAdapter.getItems().isEmpty())

        (activity as? MainView)?.displayNavigationInfo(leftValue = "", rightValue = "")
    }

    override fun getNavigator(): Navigator? {
        return if (navigator == null) {
            navigator = object: SupportAppNavigator(activity, childFragmentManager, R.id.container) {
                override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
                    return when (screenKey) {
                        else -> null
                    }
                }

                override fun createFragment(screenKey: String, data: Any?): Fragment? {
                    return when (screenKey) {
                        else -> null
                    }
                }

                override fun exit() {
                    super.exit()
                    activity?.let { (it as RouterProvider).getRouter().exit() }
                }
            }

            navigator as SupportAppNavigator
        } else {
            navigator!!
        }
    }

    // MARK: - Base implementation
    override fun doubleTap() {

    }

    override fun updateContainer() {

    }

    // MARK: - View implementation
    override fun setupWorkers(data: List<WorkerHub>) {
        if (mAdapter.hasItems) {
            mAdapter.updateItems(itemsList = data)
        } else {
            mAdapter.setList(dataList = data)
        }

        recyclerWorkers.visibility = View.VISIBLE
        txtWorkersNoItems.visibility = View.GONE
    }

    override fun setupNoItems() {
        recyclerWorkers.visibility = View.GONE
        txtWorkersNoItems.visibility = View.VISIBLE
    }

    override fun startLoading() {
        cpvWorkers.visibility = View.VISIBLE
        txtWorkersNoItems.visibility = View.GONE
        recyclerWorkers.visibility = View.GONE
    }

    override fun endLoading() {
        cpvWorkers.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}