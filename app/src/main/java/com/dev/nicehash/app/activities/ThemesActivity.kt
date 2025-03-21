package com.dev.nicehash.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.presenters.ThemesPresenter
import com.dev.nicehash.app.views.ThemesView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.Theme
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 24.06.18.
 * Activity for theme settings
 */
class ThemesActivity: MvpAppCompatActivity(), ThemesView {
    private val TAG = ThemesActivity::class.java.simpleName

    @Inject lateinit var configurationRepository: ConfigurationRepository
    @Inject lateinit var routerTheme: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var themesPresenter: ThemesPresenter
    @ProvidePresenter
    fun provideThemesPresenter(): ThemesPresenter {
        return ThemesPresenter(configurationRepository = configurationRepository)
    }

    // MARK: - Navigation
//    private val navigator = object: AppNavigator(this@ThemesActivity, supportFragmentManager, 0) {
//        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
//            return null
//        }
//
//        override fun createFragment(screenKey: String?, data: Any?): Fragment? {
//            return null
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@ThemesActivity)
        if (intent?.extras == null) {
            when (App.theme) {
                Theme.LightBlue -> setTheme(R.style.LightBlueTheme)
                Theme.LightGreen -> setTheme(R.style.LightGreenTheme)
                Theme.LightOrange -> setTheme(R.style.LightOrangeTheme)
                Theme.LightRed -> setTheme(R.style.LightRedTheme)
                Theme.LightPurple -> setTheme(R.style.LightPurpleTheme)
                Theme.DarkBlue -> setTheme(R.style.DarkBlueTheme)
                Theme.DarkGreen -> setTheme(R.style.DarkGreenTheme)
                Theme.DarkOrange -> setTheme(R.style.DarkOrangeTheme)
                Theme.DarkRed -> setTheme(R.style.DarkRedTheme)
                Theme.DarkPurple -> setTheme(R.style.DarkPurpleTheme)
            }
        } else {
//            val isLight = intent.extras.get(Keys.Light.value) as? Boolean ?: true
//            val color = intent.extras.get(Keys.Color.value) as? Int ?: 0
//            when (isLight) {
//                true -> {
//                    when (color) {
//                        0 -> setTheme(R.style.LightBlueTheme)
//                        1 -> setTheme(R.style.LightGreenTheme)
//                        2 -> setTheme(R.style.LightOrangeTheme)
//                        3 -> setTheme(R.style.LightRedTheme)
//                        4 -> setTheme(R.style.LightPurpleTheme)
//                    }
//                }
//                false -> {
//                    when (color) {
//                        0 -> setTheme(R.style.DarkBlueTheme)
//                        1 -> setTheme(R.style.DarkGreenTheme)
//                        2 -> setTheme(R.style.DarkOrangeTheme)
//                        3 -> setTheme(R.style.DarkRedTheme)
//                        4 -> setTheme(R.style.DarkPurpleTheme)
//                    }
//                }
//            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (intent?.extras == null) {
            themesPresenter.setupAppTheme()
        } else {
            themesPresenter.setColor(intent?.extras?.get(Keys.Color.value) as? Int ?: 0)
            themesPresenter.setLight(intent?.extras?.get(Keys.Light.value) as? Boolean ?: true)
            themesPresenter.setupView()
        }

//        imgThemesBack.setOnClickListener { onBackPressed() }
//        flLightTab.setOnClickListener { themesPresenter.onTabClick(position = 0) }
//        flDarkTab.setOnClickListener { themesPresenter.onTabClick(position = 1) }
//        flThemesBluePalette.setOnClickListener { themesPresenter.onPaletteClick(position = 0) }
//        flThemesGreenPalette.setOnClickListener { themesPresenter.onPaletteClick(position = 1) }
//        flThemesOrangePalette.setOnClickListener { themesPresenter.onPaletteClick(position = 2) }
//        flThemesRedPalette.setOnClickListener { themesPresenter.onPaletteClick(position = 3) }
//        flThemesPurplePalette.setOnClickListener { themesPresenter.onPaletteClick(position = 4) }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
//        navigatorHolder.setNavigator(navigator)
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

    // MARK: - View implementation
    override fun setupTab(position: Int) {
        themesPresenter.setupTheme(isLight = position == 0)
    }

    override fun setupView(isLight: Boolean, color: Int) {
//        chartThemes.fitScreen()
//        chartThemes.description.isEnabled = false
//        chartThemes.legend.isEnabled = false
//        chartThemes.setViewPortOffsets(0f, 0f, 0f, 0f)
//        chartThemes.isDoubleTapToZoomEnabled = false
//
//        val xAxis = chartThemes.xAxis
//        xAxis.setDrawLimitLinesBehindData(true);
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.textColor = Color.WHITE
//        xAxis.disableGridDashedLine()
//        xAxis.setDrawGridLines(false)
//        xAxis.gridColor = Color.argb(204, 255, 255, 255)
//        xAxis.axisLineColor = Color.TRANSPARENT
//        xAxis.setAvoidFirstLastClipping(true)
//
//        val averageLine = LimitLine(0.0004f)
//        averageLine.lineColor = Color.WHITE
//        averageLine.lineWidth = 4f
//        averageLine.enableDashedLine(WindowUtils.convertDpToPixel(4f, applicationContext),
//                WindowUtils.convertDpToPixel(2f, applicationContext), 2f)
//        averageLine.lineWidth = 2f
//
//        chartThemes.axisRight.isEnabled = false
//        chartThemes.axisRight.spaceBottom = 0f
//        chartThemes.xAxis.isEnabled = false
//
//        chartThemes.axisLeft.isEnabled = true
//        chartThemes.axisLeft.addLimitLine(averageLine)
//        chartThemes.axisLeft.setLabelCount(9, true)
//        chartThemes.axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
//        chartThemes.axisLeft.gridLineWidth = 0.5f
//        chartThemes.axisLeft.textSize = 10f
//        chartThemes.axisLeft.setDrawTopYLabelEntry(false)
//        chartThemes.axisLeft.setDrawZeroLine(false)
//        chartThemes.axisLeft.valueFormatter = IAxisValueFormatter { value, axis ->
//            if (value < 0.00001f) {
//                return@IAxisValueFormatter ""
//            }
//
//            String.format("%.05f", value)
//        }
//
//        val emptyData = ArrayList<Entry>()
//        emptyData.add(Entry(0f, 0.0001f))
//        emptyData.add(Entry(1f, 0.0007f))
//        emptyData.add(Entry(2f, 0.0010f))
//        emptyData.add(Entry(3f, 0.0006f))
//        emptyData.add(Entry(4f, 0.0006f))
//        emptyData.add(Entry(5f, 0.0007f))
//        emptyData.add(Entry(6f, 0.0007f))
//        emptyData.add(Entry(7f, 0.0010f))
//        emptyData.add(Entry(8f, 0.0011f))
//        emptyData.add(Entry(9f, 0.0005f))
//        emptyData.add(Entry(10f, 0.0010f))
//        emptyData.add(Entry(11f, 0.0006f))
//        emptyData.add(Entry(12f, 0.0006f))
//        emptyData.add(Entry(13f, 0.0007f))
//        emptyData.add(Entry(14f, 0.0010f))
//        emptyData.add(Entry(15f, 0.0007f))
//        emptyData.add(Entry(16f, 0.0011f))
//        emptyData.add(Entry(17f, 0.0007f))
//        emptyData.add(Entry(18f, 0.0011f))
//
//        val dataSet = LineDataSet(emptyData, "Template Data")
//        dataSet.setDrawFilled(true)
//        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        dataSet.setDrawCircles(false)
//        dataSet.setDrawValues(false)
//        dataSet.setDrawHorizontalHighlightIndicator(false)
//
//        dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext, when (App.theme) {
//            Theme.LightBlue -> R.drawable.light_chart_gradient_blue
//            Theme.LightGreen -> R.drawable.light_chart_gradient_green
//            Theme.LightOrange -> R.drawable.light_chart_gradient_orange
//            Theme.LightRed -> R.drawable.light_chart_gradient_red
//            Theme.LightPurple -> R.drawable.light_chart_gradient_purple
//            Theme.DarkPurple -> R.drawable.dark_chart_gradient_purple
//            Theme.DarkRed -> R.drawable.dark_chart_gradient_red
//            Theme.DarkOrange -> R.drawable.dark_chart_gradient_orange
//            Theme.DarkGreen -> R.drawable.dark_chart_gradient_green
//            Theme.DarkBlue -> R.drawable.dark_chart_gradient_blue
//        })
//
//        val lineData = LineData(dataSet)
//        chartThemes.data = lineData
//        chartThemes.invalidate()
//
//        val xMax = chartThemes.data.getDataSetByIndex(0).xMax
//        val xMin = 0f
//        xAxis.axisMaximum = xMax
//        xAxis.axisMinimum = xMin
//        chartThemes.invalidate()
//
//        if (isLight) {
//            chartThemes.axisLeft.gridColor = Color.parseColor("#0D000000")
//            chartThemes.axisLeft.axisLineColor = ContextCompat.getColor(applicationContext, R.color.light_background_secondary)
//            chartThemes.axisLeft.textColor = ContextCompat.getColor(applicationContext, R.color.light_text_color_primary)
//            dataSet.highLightColor = ContextCompat.getColor(applicationContext, R.color.light_chart_selection_line)
//            setStatusBarColor(R.color.light_status_bar_color)
//            (viewThemesInverse.layoutParams as? FrameLayout.LayoutParams)?.marginStart = WindowUtils.convertDpToPixel(40f, applicationContext).toInt()
//            (viewThemesInverse.layoutParams as? FrameLayout.LayoutParams)?.marginEnd = 0
//            when (color) {
//                0 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_blue_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_chart_gradient_blue)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.light_blue_color_primary)
//                    viewThemesBlue.visibility = View.VISIBLE
//                }
//                1 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_green_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_chart_gradient_green)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.light_green_color_primary)
//                    viewThemesGreen.visibility = View.VISIBLE
//                }
//                2 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_orange_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_chart_gradient_orange)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.light_orange_color_primary)
//                    viewThemesOrange.visibility = View.VISIBLE
//                }
//                3 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_red_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_chart_gradient_red)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.light_red_color_primary)
//                    viewThemesRed.visibility = View.VISIBLE
//                }
//                4 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_purple_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.light_chart_gradient_purple)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.light_purple_color_primary)
//                    viewThemesPurple.visibility = View.VISIBLE
//                }
//            }
//        } else {
//            chartThemes.axisLeft.gridColor = Color.parseColor("#1AFFFFFF")
//            chartThemes.axisLeft.axisLineColor = ContextCompat.getColor(applicationContext, R.color.dark_background_secondary)
//            chartThemes.axisLeft.textColor = ContextCompat.getColor(applicationContext, R.color.dark_text_color_primary)
//            dataSet.highLightColor = ContextCompat.getColor(applicationContext, R.color.dark_chart_selection_line)
//            setStatusBarColor(R.color.dark_status_bar_color)
//            (viewThemesInverse.layoutParams as? FrameLayout.LayoutParams)?.marginStart = 0
//            (viewThemesInverse.layoutParams as? FrameLayout.LayoutParams)?.marginEnd = WindowUtils.convertDpToPixel(40f, applicationContext).toInt()
//            when (color) {
//                0 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_blue_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_chart_gradient_blue)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.dark_blue_color_primary)
//                    viewThemesBlue.visibility = View.VISIBLE
//                }
//                1 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_green_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_chart_gradient_green)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.dark_green_color_primary)
//                    viewThemesGreen.visibility = View.VISIBLE
//                }
//                2 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_orange_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_chart_gradient_orange)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.dark_orange_color_primary)
//                    viewThemesOrange.visibility = View.VISIBLE
//                }
//                3 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_red_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_chart_gradient_red)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.dark_red_color_primary)
//                    viewThemesRed.visibility = View.VISIBLE
//                }
//                4 -> {
//                    flThemesOneDay.background = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_purple_chart_time_background)
//                    dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext,
//                            R.drawable.dark_chart_gradient_purple)
//                    dataSet.color = ContextCompat.getColor(applicationContext,
//                            R.color.dark_purple_color_primary)
//                    viewThemesPurple.visibility = View.VISIBLE
//                }
//            }
//        }
    }

    override fun switchTheme(isLight: Boolean, color: Int) {
        val intent = Intent(applicationContext, ThemesActivity::class.java)
        intent.putExtra(Keys.Light.value, isLight)
        intent.putExtra(Keys.Color.value, color)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}