package com.dev.nicehash.app.activities

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.StatusAdapter
import com.dev.nicehash.app.presenters.StatusPresenter
import com.dev.nicehash.app.views.StatusView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Status
import com.dev.nicehash.helpers.ListConfig
import kotlinx.android.synthetic.main.activity_service_status.*

/**
 * Created by Alex Gladkov on 24.06.18.
 * Activity for service status
 */
class ServiceStatusActivity: BaseActivity(), StatusView {
    private val mAdapter = StatusAdapter()

    @InjectPresenter
    lateinit var statusPresenter: StatusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@ServiceStatusActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_status)

        imgServiceBack.setOnClickListener { onBackPressed() }

        val listConfig = ListConfig.Builder(adapter = mAdapter)
                .setHasFixedSize(isFixedSize = true)
                .setHasNestedScroll(isNestedScroll = false)
                .build(context = this@ServiceStatusActivity)

        listConfig.applyConfig(context = this@ServiceStatusActivity, recyclerView = recyclerServiceStatus)

        statusPresenter.fetchStatuses()
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    // MARK: - View implementation
    override fun setupView(data: List<Status>) {
        if (mAdapter.hasItems) {
            mAdapter.updateItems(itemsList = data)
        } else {
            mAdapter.setList(dataList = data)
        }
    }
}