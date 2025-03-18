package com.dev.nicehash.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.SettingsPresenter
import com.dev.nicehash.app.views.SettingsView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.enums.ScreenKeys
import com.dev.nicehash.helpers.EnumCollections
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 23.06.18.
 * Activity for user settings
 */
class SettingsActivity : BaseActivity(), RouterProvider, SettingsView {
    private val TAG = ChooseActivity::class.java.simpleName

    // MARK: - Injects
    @Inject lateinit var routerSettings: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var configurationRepository: ConfigurationRepository

    // MARK: - Presenter
    @InjectPresenter lateinit var settingsPresenter: SettingsPresenter

    @ProvidePresenter
    fun provideSettingsPresenter(): SettingsPresenter {
        return SettingsPresenter(router = routerSettings, configurationRepository = configurationRepository)
    }

    // MARK: - Navigation
    private val navigator = object : Navigator {
        override fun applyCommands(commands: Array<out Command>) {
            commands.forEach {
                applyCommand(it)
            }
        }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Back -> finish()
//            is SystemMessage -> Toast.makeText(applicationContext, command.message, Toast.LENGTH_SHORT).show()
            is Replace -> {
                when (command.screen.screenKey) {
                    ScreenKeys.Notifications.value -> {
                        startActivity(Intent(applicationContext, NotificationsActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    ScreenKeys.Themes.value -> {
                        startActivity(Intent(applicationContext, ThemesActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    ScreenKeys.Tab.value -> {
//                        val popupMenu = PopupMenu(applicationContext, txtTabValue)
//                        val menuInflater = popupMenu.menuInflater
//
//                        popupMenu.setOnMenuItemClickListener { menuItem ->
//                            when (menuItem.itemId) {
//                                R.id.default_tab_income -> {
////                                    txtTabValue.text = getString(R.string.settings_income)
//                                    currentConfiguration.defaultTab = 0
//                                }
//                                R.id.default_tab_balance -> {
////                                    txtTabValue.text = getString(R.string.settings_balance)
//                                    currentConfiguration.defaultTab = 1
//                                }
//                                R.id.default_tab_payouts -> {
////                                    txtTabValue.text = getString(R.string.settings_payouts)
//                                    currentConfiguration.defaultTab = 2
//                                }
//                                R.id.default_tab_workers -> {
////                                    txtTabValue.text = getString(R.string.settings_workers)
//                                    currentConfiguration.defaultTab = 3
//                                }
//                            }
//
//                            settingsPresenter.updateConfiguration(configuration = currentConfiguration)
//                            false
//                        }
//
//                        menuInflater.inflate(R.menu.menu_default_tab, popupMenu.menu)
//                        popupMenu.show()
                    }

                    ScreenKeys.Language.value -> {
//                        val popupMenu = PopupMenu(applicationContext, txtLanguageValue)
//                        val menuInflater = popupMenu.menuInflater

//                        popupMenu.setOnMenuItemClickListener { menuItem ->
//                            when (menuItem.itemId) {
//                                R.id.language_english -> {
////                                    txtLanguageValue.text = getString(R.string.settings_language_english)
//                                    currentConfiguration.defaultLanguage = EnumCollections.Language.English
//                                }
//                                R.id.language_chinese -> {
////                                    txtLanguageValue.text = getString(R.string.settings_language_chinese)
//                                    currentConfiguration.defaultLanguage = EnumCollections.Language.Chinese
//                                }
//                                R.id.language_russian -> {
////                                    txtLanguageValue.text = getString(R.string.settings_language_russian)
//                                    currentConfiguration.defaultLanguage = EnumCollections.Language.Russian
//                                }
//                            }
//
//                            settingsPresenter.updateConfiguration(configuration = currentConfiguration)
//                            false
//                        }
//
//                        menuInflater.inflate(R.menu.menu_language, popupMenu.menu)
//                        popupMenu.show()
                    }

                    ScreenKeys.Currencies.value -> {
                        startActivity(Intent(applicationContext, CurrenciesActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    ScreenKeys.Service.value -> {
                        startActivity(Intent(applicationContext, ServiceStatusActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    ScreenKeys.Purchases.value -> {
                        startActivity(Intent(applicationContext, PurchasesActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    ScreenKeys.About.value -> {
                        startActivity(Intent(applicationContext, AboutActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@SettingsActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

//        imgSettingsBack.setOnClickListener { settingsPresenter.onBackClick() }
//
//        btnSettingsNotifications.setOnClickListener { settingsPresenter.performClick(id = 0) }
//        btnSettingsThemes.setOnClickListener { settingsPresenter.performClick(id = 1) }
//        btnSettingsDefaultTab.setOnClickListener { settingsPresenter.performClick(id = 2) }
//        btnSettingsLanguage.setOnClickListener { settingsPresenter.performClick(id = 3) }
//        btnSettingsCurrencies.setOnClickListener { settingsPresenter.performClick(id = 4) }
//        btnSettingsService.setOnClickListener { settingsPresenter.performClick(id = 5) }
//        btnSettingsPurchases.setOnClickListener { settingsPresenter.performClick(id = 6) }
//        btnSettingsAbout.setOnClickListener { settingsPresenter.performClick(id = 7) }
    }

    override fun onResume() {
        super.onResume()
        settingsPresenter.fetchConfiguration()

        if (App.isSettingsNeedsToReload) {
            App.isSettingsNeedsToReload = false
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    // MARK: - RouterProvider implementation
    override fun getRouter(): Router {
        return routerSettings
    }

    // MARK: - View implementation
    private var currentConfiguration = Configuration.defaultInstance()
    override fun setupConfiguration(configuration: Configuration) {
        this.currentConfiguration = configuration
//        txtTabValue.text = when (configuration.defaultTab) {
//            1 -> getString(R.string.settings_balance)
//            2 -> getString(R.string.settings_payouts)
//            3 -> getString(R.string.settings_workers)
//            else -> getString(R.string.settings_income)
//        }

//        txtLanguageValue.text = getString(configuration.defaultLanguage.title)
    }
}