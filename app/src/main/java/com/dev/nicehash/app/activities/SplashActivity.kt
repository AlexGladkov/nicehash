package com.dev.nicehash.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.presenters.SplashPresenter
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.views.SplashView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.domain.repositories.CurrencyRepository
import com.dev.nicehash.domain.repositories.GeneralRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 16.07.18.
 * Start activity (all logic threads starts from here)
 */
class SplashActivity: BaseActivity(), SplashView {
    private val TAG = SplashActivity::class.java.simpleName

    // MARK: - Presenter setup
    @Inject lateinit var configurationRepository: ConfigurationRepository
    @Inject lateinit var minerRepository: MinerRepository
    @Inject lateinit var generalRepository: GeneralRepository
    @Inject lateinit var currencyRepositorry: CurrencyRepository
    @Inject lateinit var exchangeServer: ExchangeServer

    @InjectPresenter lateinit var splashPresenter: SplashPresenter
    @ProvidePresenter
    fun provideSplashPresenter(): SplashPresenter {
        return SplashPresenter(configurationRepository = configurationRepository,
                minerRepository = minerRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@SplashActivity)
        super.onCreate(savedInstanceState)
        Log.e(TAG, "on create")
        setContentView(R.layout.activity_splash)

        splashPresenter.fetchConfiguration()
        currencyRepositorry.fetchExchangeRates()
    }

    // MARK: - View implementation
    override fun setupConfiguration(configuration: Configuration, miner: Miner) {
        Log.e(TAG, "miner name ${miner.name}, id ${miner.id}")
        App.theme = configuration.theme

        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        mainIntent.putExtra(Keys.Configuration.value, configuration)
        mainIntent.putExtra(Keys.Miner.value, miner.hash)
        mainIntent.putExtra(Keys.MinerTitle.value, miner.name)
        startActivity(mainIntent)
    }
}