package com.dev.nicehash.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.adapters.MinerAdapter
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.ChoosePresenter
import com.dev.nicehash.app.views.ChooseView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.base.BaseAdapterCallback
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.models.Miner
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.ScreenKeys
import com.dev.nicehash.enums.Theme
import com.dev.nicehash.helpers.EnumCollections
import com.dev.nicehash.helpers.ListConfig
import kotlinx.android.synthetic.main.activity_choose.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 23.06.18.
 * Activity for multi accounting
 */
class ChooseActivity: MvpAppCompatActivity(), RouterProvider, ChooseView {
    private val TAG = ChooseActivity::class.java.simpleName

    // MARK: - Injects
    @Inject lateinit var routerChoose: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var minerRepository: MinerRepository
    @Inject lateinit var configurationRepository: ConfigurationRepository

    @InjectPresenter lateinit var choosePresenter: ChoosePresenter
    @ProvidePresenter
    fun provideChoosePresenter(): ChoosePresenter {
        return ChoosePresenter(router = routerChoose, minerRepository = minerRepository,
                configurationRepository = configurationRepository)
    }

    // MARK: - UI Setup
    private val mAdapter = MinerAdapter(minerClick = object: MinerAdapter.MinerClick {
        override fun onEditClick(miner: Miner) {
            choosePresenter.editClick(miner = miner)
        }

        override fun onDeleteClick(miner: Miner) {
            choosePresenter.deleteClick(miner = miner)
        }
    })

    // MARK: - Navigation
    private val navigator = Navigator { commands ->
        commands.forEach {
            applyCommand(it)
        }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Back -> finish()
            is SystemMessage -> Toast.makeText(applicationContext, command.message, Toast.LENGTH_SHORT).show()
            is Replace -> {
                when (command.screenKey) {
                    ScreenKeys.Settings.value -> {
                        val settingsIntent = Intent(applicationContext, SettingsActivity::class.java)
                        settingsIntent.putExtra(Keys.Configuration.name, currentConfiguration)
                        startActivity(settingsIntent)
                    }

                    ScreenKeys.Splash.value -> {
                        val splashIntent = Intent(applicationContext, SplashActivity::class.java)
                        splashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(splashIntent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }

                    ScreenKeys.Add.value -> {
                        startActivity(Intent(applicationContext, AddActivity::class.java))
                    }

                    ScreenKeys.Edit.value -> {
                        (command.transitionData as? Bundle)?.let {
                            val editIntent = Intent(applicationContext, AddActivity::class.java)
                            editIntent.putExtra(Keys.Miner.value, it)
                            startActivity(editIntent)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@ChooseActivity)
        if (App.isDark) { setTheme(R.style.DarkTransparent) } else { setTheme(R.style.LightTransparent) }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setStatusBarColor(when {
            App.theme == Theme.DarkPurple -> R.color.dark_status_bar_color
            App.theme == Theme.DarkBlue -> R.color.dark_status_bar_color
            App.theme == Theme.DarkRed -> R.color.dark_status_bar_color
            App.theme == Theme.DarkOrange -> R.color.dark_status_bar_color
            App.theme == Theme.DarkGreen -> R.color.dark_status_bar_color
            else -> R.color.light_status_bar_color
        })

        choosePresenter.handleConfiguration(extras = intent?.extras)

        mAdapter.attachCallback(object: BaseAdapterCallback<Miner> {
            override fun onItemClick(model: Miner, view: View) {
                choosePresenter.onMinerClick(miner = model)
            }

            override fun onLongClick(model: Miner, view: View): Boolean {
                return false
            }
        })

        when (App.theme) {
            Theme.DarkGreen -> {
                tbChoose.background = ContextCompat.getDrawable(applicationContext, R.color.dark_background_primary)
                btnChooseSettings.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
                imgChooseAdd.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
            }
            Theme.DarkOrange -> {
                tbChoose.background = ContextCompat.getDrawable(applicationContext, R.color.dark_background_primary)
                btnChooseSettings.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
                imgChooseAdd.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
            }
            Theme.DarkRed -> {
                tbChoose.background = ContextCompat.getDrawable(applicationContext, R.color.dark_background_primary)
                btnChooseSettings.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
                imgChooseAdd.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
            }
            Theme.DarkPurple -> {
                tbChoose.background = ContextCompat.getDrawable(applicationContext, R.color.dark_background_primary)
                btnChooseSettings.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
                imgChooseAdd.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
            }
            Theme.DarkBlue -> {
                tbChoose.background = ContextCompat.getDrawable(applicationContext, R.color.dark_background_primary)
                btnChooseSettings.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
                imgChooseAdd.setColorFilter(ContextCompat.getColor(applicationContext, R.color.dark_tint_color))
            }
            else -> {
                tbChoose.background = ContextCompat.getDrawable(applicationContext, R.color.light_background_primary)
                btnChooseSettings.setColorFilter(ContextCompat.getColor(applicationContext, R.color.light_tint_color))
                imgChooseAdd.setColorFilter(ContextCompat.getColor(applicationContext, R.color.light_tint_color))
            }
        }

        flChoose.setOnClickListener { onBackPressed() }
        imgChooseAdd.setOnClickListener { choosePresenter.onAddClick() }

        val listConfig = ListConfig.Builder(mAdapter)
                .setHasFixedSize(true)
                .setHasNestedScroll(false)
                .build(applicationContext)
        listConfig.applyConfig(applicationContext, recyclerMiners)

        btnChooseSettings.setOnClickListener { choosePresenter.onGearClick() }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onResume() {
        super.onResume()
        choosePresenter.loadMiners()
        if (App.isChooseNeedsToReload) {
            App.isChooseNeedsToReload = false
            finish()
            overridePendingTransition(0, 0)
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

    private fun setStatusBarColor(color: Int) {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = resources.getColor(color)
    }

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    // MARK: - RouterProvider implementation
    override fun getRouter(): Router {
        return routerChoose
    }

    // MARK: - View implementation
    override fun setupMiners(list: List<Miner>) {
        if (mAdapter.hasItems) {
            mAdapter.updateItems(itemsList = list)
        } else {
            mAdapter.setList(dataList = list)
        }
    }

    private var currentConfiguration = Configuration.defaultInstance()
    override fun setupConfiguration(configuration: Configuration) {
        this.currentConfiguration = configuration
    }
}