package com.dev.nicehash.app.activities

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.SwitchAdapter
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 24.06.18.
 * Activity for notifications settings
 */
class NotificationsActivity : BaseActivity() {
    private val TAG = NotificationsActivity::class.java.simpleName

    // MARK: - Presenter setup
    @Inject lateinit var configurationRepository: ConfigurationRepository

    // MARK: - UI Setup

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@NotificationsActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

//        imgNotificationsBack.setOnClickListener { onBackPressed() }

        configurationRepository.fetchConfiguration()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configuration ->
                    setupConfiguration(configuration = configuration)
                }, { error ->
                    error.printStackTrace()
                    setupConfiguration(configuration = Configuration.defaultInstance())
                })
    }

    private fun onChangeClick(position: Int, isChecked: Boolean) {
        when (position) {
            0 -> configuration.isPayments = isChecked
            1 -> configuration.isMaintenance = isChecked
            2 -> configuration.isTop = isChecked
            3 -> configuration.isBottom = isChecked
            4 -> configuration.isStrix = isChecked
            5 -> configuration.isSound = isChecked
            6 -> configuration.isVibro = isChecked
        }

        Log.e(TAG, "configuration bottom ${configuration.isBottom}")

        configurationRepository.updateConfiguration(configuration = configuration)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error ->
                    Log.e(TAG, "update error ${error.localizedMessage}")
                })
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    private lateinit var configuration: Configuration
    private fun setupConfiguration(configuration: Configuration) {
        this.configuration = configuration
        Log.e(TAG, "configuration bottom ${configuration.isBottom}")
//        swNotificationsPayments.isChecked = configuration.isPayments
//        swNotificationsMaintenance.isChecked = configuration.isMaintenance
//
//        swNotificationsTop.isChecked = configuration.isTop
//        swNotificationsBottom.isChecked = configuration.isBottom
//        swNotificationsStrix.isChecked = configuration.isStrix
//
//        swNotificationsSound.isChecked = configuration.isSound
//        swNotificationsVibro.isChecked = configuration.isVibro
//
//        swNotificationsPayments.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 0, isChecked = isChecked)
//        }
//        swNotificationsMaintenance.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 1, isChecked = isChecked)
//        }
//        swNotificationsTop.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 2, isChecked = isChecked)
//        }
//        swNotificationsBottom.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 3, isChecked = isChecked)
//        }
//        swNotificationsStrix.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 4, isChecked = isChecked)
//        }
//        swNotificationsSound.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 5, isChecked = isChecked)
//        }
//        swNotificationsVibro.setOnCheckedChangeListener { _, isChecked ->
//            onChangeClick(position = 6, isChecked = isChecked)
//        }
    }
}