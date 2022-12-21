package com.dev.nicehash.app.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.fragments.*
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.presenters.MainPresenter
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.app.views.MainView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Balance
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.ScreenKeys
import com.dev.nicehash.enums.Theme
import com.dev.nicehash.helpers.PaintUtils
import com.dev.nicehash.helpers.SizeContract
import com.dev.nicehash.helpers.WindowUtils
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage
import java.text.DecimalFormat
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, RouterProvider, SmartTabLayout.TabProvider {
    private val TAG = MainActivity::class.java.simpleName

    // MARK: - Injects
    @Inject lateinit var routerMain: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var minerRepository: MinerRepository
    @Inject lateinit var exchangeServer: ExchangeServer

    // MARK: - Presenter setup
    @InjectPresenter lateinit var mainPresenter: MainPresenter

    // MARK: - Fragments setup
    private var incomeFragment: IncomeFragment? = null
    private var balanceFragment: BalanceFragment? = null
    private var payoutsFragment: PayoutsFragment? = null
    private var workersFragment: WorkersFragment? = null

    // MARK: - Variables
    private var currentPosition = 0

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return MainPresenter(router = routerMain, minerRepository = minerRepository)
    }

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
                    ScreenKeys.Choose.value -> {
                        val chooseIntent = Intent(applicationContext, ChooseActivity::class.java)
                        chooseIntent.putExtra(Keys.Configuration.value, currentConfiguration)
                        startActivity(chooseIntent)
                        overridePendingTransition(0, 0)
                    }

                    ScreenKeys.Detail.value -> {
                        val balance = (command.transitionData as? Balance)
                        balance.let {
                            val detailIntent = Intent(applicationContext, DetailActivity::class.java)
                            detailIntent.putExtra(Keys.Balance.value, it)
                            startActivity(detailIntent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }

                    ScreenKeys.Income.value -> {
                        supportFragmentManager.beginTransaction()
                                .detach(balanceFragment)
                                .detach(payoutsFragment)
                                .detach(workersFragment)
                                .attach(incomeFragment)
                                .commitNow()
                    }

                    ScreenKeys.Balance.value -> {
                        supportFragmentManager.beginTransaction()
                                .detach(incomeFragment)
                                .detach(payoutsFragment)
                                .detach(workersFragment)
                                .attach(balanceFragment)
                                .commitNow()
                    }

                    ScreenKeys.Payouts.value -> {
                        supportFragmentManager.beginTransaction()
                                .detach(balanceFragment)
                                .detach(incomeFragment)
                                .detach(workersFragment)
                                .attach(payoutsFragment)
                                .commitNow()
                    }

                    ScreenKeys.Workers.value -> {
                        supportFragmentManager.beginTransaction()
                                .detach(balanceFragment)
                                .detach(payoutsFragment)
                                .detach(incomeFragment)
                                .attach(workersFragment)
                                .commitNow()
                    }
                }
            }
        }
    }

    private var currentConfiguration = Configuration.defaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@MainActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.handleConfiguration(extras = intent?.extras)

        imgMainDevice.setOnClickListener {
            mainPresenter.onDeviceClick()
        }

        initFragments()
        tuneChart()

        nsvMain.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            mainPresenter.handleScrolling(scrollY = scrollY, daysBarHeight =
            WindowUtils.convertDpToPixel(dp = SizeContract.daysBarHeight, context = applicationContext),
                    introBarHeight = WindowUtils.convertDpToPixel(dp = SizeContract.introBarHeight,
                            context = applicationContext))
        })

        txtMainTitle.text = intent?.extras?.getString(Keys.MinerTitle.value).orEmpty()
        flMainOneHour.setOnClickListener { mainPresenter.onChartTimeClick(position = 0,
                miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1) }
        flMainSixHours.setOnClickListener { mainPresenter.onChartTimeClick(position = 1,
                miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1) }
        flMainTwelveHours.setOnClickListener { mainPresenter.onChartTimeClick(position = 2,
                miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1) }
        flMainOneDay.setOnClickListener { mainPresenter.onChartTimeClick(position = 3,
                miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1) }
        flMainThreeDays.setOnClickListener { mainPresenter.onChartTimeClick(position = 4,
                miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1) }
        flMainAll.setOnClickListener { mainPresenter.onChartTimeClick(position = 5,
                miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1) }

        val adapter = FragmentPagerItemAdapter(
                supportFragmentManager, FragmentPagerItems.with(this)
                .add("", DummyFragment::class.java)
                .add("", DummyFragment::class.java)
                .add("", DummyFragment::class.java)
                .add("", DummyFragment::class.java)
                .create())

        stbMain.setCustomTabView(this)
        vpMain.adapter = adapter
        stbMain.setViewPager(vpMain)
        stbMain.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val oldTab = stbMain.getTabAt(currentPosition)
                val currentTab = stbMain.getTabAt(position)

                val oldView = oldTab.findViewById<View>(R.id.tab_layout) as RelativeLayout
                val currentView = currentTab.findViewById<View>(R.id.tab_layout) as RelativeLayout

                val textView = currentView.findViewById<View>(R.id.tab_txt) as TextView
                val oldTextView = oldView.findViewById<View>(R.id.tab_txt) as TextView

                when (position) {
                    0 -> textView.text = getString(R.string.tab_income)
                    1 -> textView.text = getString(R.string.tab_balance)
                    2 -> textView.text = getString(R.string.tab_payouts)
                    3 -> textView.text = getString(R.string.tab_workers)
                }

                oldView.background = ContextCompat.getDrawable(applicationContext,
                        R.drawable.tab_shape_transparent)
                oldTextView.alpha = 0.3f

                currentView.background = ContextCompat.getDrawable(applicationContext, if (App.isDark)
                    R.drawable.tab_shape_dark
                else
                    R.drawable.tab_shape_light)

                oldView.background = if (currentPosition == position)
                    ContextCompat.getDrawable(applicationContext, if (App.isDark) R.drawable.tab_shape_dark
                    else R.drawable.tab_shape_light)
                else
                    ContextCompat.getDrawable(applicationContext, R.drawable.tab_shape_transparent)

                textView.alpha = 1f

                currentPosition = position
                mainPresenter.onTabClick(position = position)
            }
        })

        mainPresenter.onTabClick(position = 0)
    }

    private fun tuneChart() {
        chartMain.fitScreen()
        chartMain.description.isEnabled = false
        chartMain.legend.isEnabled = false
        chartMain.setViewPortOffsets(0f, 0f, 0f, 0f)
        chartMain.isDoubleTapToZoomEnabled = false

        val xAxis = chartMain.xAxis
        xAxis.setDrawLimitLinesBehindData(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.WHITE
        xAxis.disableGridDashedLine()
        xAxis.setDrawGridLines(false)
        xAxis.gridColor = Color.argb(204, 255, 255, 255)
        xAxis.axisLineColor = Color.TRANSPARENT
        xAxis.setAvoidFirstLastClipping(true)

        val averageLine = LimitLine(0.0004f)
        averageLine.lineColor = Color.WHITE
        averageLine.lineWidth = 4f
        averageLine.enableDashedLine(WindowUtils.convertDpToPixel(4f, applicationContext),
                WindowUtils.convertDpToPixel(2f, applicationContext), 2f)
        averageLine.lineWidth = 2f

        chartMain.axisRight.isEnabled = false
        chartMain.axisRight.spaceBottom = 0f
        chartMain.xAxis.isEnabled = false

        chartMain.axisLeft.isEnabled = true
        chartMain.axisLeft.addLimitLine(averageLine)
        chartMain.axisLeft.setLabelCount(9, true)
        chartMain.axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        chartMain.axisLeft.gridColor = if (App.isDark) {
            Color.parseColor("#1AFFFFFF")
        } else {
            Color.parseColor("#0D000000")
        }
        chartMain.axisLeft.gridLineWidth = 0.5f
        chartMain.axisLeft.textSize = 10f
        chartMain.axisLeft.setDrawTopYLabelEntry(false)
        chartMain.axisLeft.setDrawZeroLine(false)
        chartMain.axisLeft.valueFormatter = IAxisValueFormatter { value, _ ->
            if (value < 0.0000001f) {
                return@IAxisValueFormatter ""
            }

            String.format("%.05f", value)
        }
        chartMain.axisLeft.axisLineColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_background_secondary
        } else {
            R.color.light_background_secondary
        })

        chartMain.axisLeft.textColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_text_color_primary
        } else {
            R.color.light_text_color_primary
        })
    }

    private fun initFragments() {
        incomeFragment = supportFragmentManager.findFragmentByTag(ScreenKeys.Income.value) as? IncomeFragment
        if (incomeFragment == null) {
            incomeFragment = IncomeFragment.getNewInstance(name = ScreenKeys.Income.value,
                    miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                    minerID = intent?.extras?.getInt(Keys.MinerID.value) ?: -1)
            supportFragmentManager.beginTransaction()
                    .add(R.id.flMain, incomeFragment, ScreenKeys.Income.value)
                    .detach(incomeFragment)
                    .commitNow()
        }

        balanceFragment = supportFragmentManager.findFragmentByTag(ScreenKeys.Balance.value) as? BalanceFragment
        if (balanceFragment == null) {
            balanceFragment = BalanceFragment.getNewInstance(name = ScreenKeys.Balance.value,
                    miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                    minerID = intent?.extras?.getInt(Keys.MinerID.value) ?: -1)
            supportFragmentManager.beginTransaction()
                    .add(R.id.flMain, balanceFragment, ScreenKeys.Balance.value)
                    .detach(balanceFragment)
                    .commitNow()
        }

        payoutsFragment = supportFragmentManager.findFragmentByTag(ScreenKeys.Payouts.value) as? PayoutsFragment
        if (payoutsFragment == null) {
            payoutsFragment = PayoutsFragment.getNewInstance(name = ScreenKeys.Payouts.value,
                    miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                    minerID = intent?.extras?.getInt(Keys.MinerID.value) ?: -1)
            supportFragmentManager.beginTransaction()
                    .add(R.id.flMain, payoutsFragment, ScreenKeys.Payouts.value)
                    .detach(payoutsFragment)
                    .commitNow()
        }

        workersFragment = supportFragmentManager.findFragmentByTag(ScreenKeys.Workers.value) as? WorkersFragment
        if (workersFragment == null) {
            workersFragment = WorkersFragment.getNewInstance(name = ScreenKeys.Workers.value,
                    miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                    minerID = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1)
            supportFragmentManager.beginTransaction()
                    .add(R.id.flMain, workersFragment, ScreenKeys.Workers.value)
                    .detach(workersFragment)
                    .commitNow()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onResume() {
        super.onResume()
        if (App.isMainNeedsToReload) {
            App.isMainNeedsToReload = false
            val mainIntent = Intent(applicationContext, MainActivity::class.java)
            mainIntent.putExtra(Keys.Configuration.value, intent?.extras?.get(Keys.Configuration.value) as? Configuration)
            mainIntent.putExtra(Keys.Miner.value, intent?.extras?.get(Keys.Miner.value) as? String)
            mainIntent.putExtra(Keys.MinerTitle.value, intent?.extras?.get(Keys.MinerTitle.value) as? String)
            startActivity(mainIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    // MARK: - RouterProvider implementation
    override fun getRouter(): Router {
        return routerMain
    }

    // MARK: - Smart layout implementation
    override fun createTabView(container: ViewGroup, position: Int, adapter: PagerAdapter?): View {
        val inflater = LayoutInflater.from(container.context)
        val tab = inflater.inflate(R.layout.tab_item, container, false)
        val textView = tab.findViewById<View>(R.id.tab_txt) as TextView
        val layoutView = tab.findViewById<View>(R.id.tab_layout) as RelativeLayout

        textView.setTextColor(ContextCompat.getColor(applicationContext, if (App.isDark)
            R.color.dark_text_color_primary
        else
            R.color.light_text_color_primary))

        layoutView.background = if (currentPosition == position)
            ContextCompat.getDrawable(applicationContext, if (App.isDark) R.drawable.tab_shape_dark
            else R.drawable.tab_shape_light)
        else
            ContextCompat.getDrawable(applicationContext, R.drawable.tab_shape_transparent)

        when (position) {
            0 -> textView.text = getString(R.string.tab_income)
            1 -> textView.text = getString(R.string.tab_balance)
            2 -> textView.text = getString(R.string.tab_payouts)
            3 -> textView.text = getString(R.string.tab_workers)
        }

        if (currentPosition != position) textView.alpha = 0.3f

        return tab
    }

    // MARK: - View implementation
    override fun displayNavigationInfo(leftValue: String, rightValue: String) {
        txtMainNavLeftTitle.text = leftValue
        txtMainNavRightTitle.text = rightValue
    }

    override fun setupNavigation(position: Int) {
        when (position) {
            0 -> {
                txtMainNavLeftSubtitle.text = getString(R.string.navigation_bar_income_left)
                txtMainNavRightSubtitle.text = "RUB/Day"
            }
            1 -> {
                txtMainNavLeftSubtitle.text = getString(R.string.navigation_bar_balance_left)
                txtMainNavRightSubtitle.text = getString(R.string.navigation_bar_balance_right)
            }
            2 -> {
                txtMainNavLeftSubtitle.text = getString(R.string.navigation_bar_payout_left)
                txtMainNavRightSubtitle.text = getString(R.string.navigation_bar_payout_right)
            }
            3 -> {
                txtMainNavLeftSubtitle.text = getString(R.string.navigation_bar_workers_left)
                txtMainNavRightSubtitle.text = getString(R.string.navigation_bar_workers_right)
            }
        }
    }

    override fun setupConfiguration(configuration: Configuration) {
        this.currentConfiguration = configuration
        vpMain.currentItem = configuration.defaultTab
        val screenHeight = WindowUtils.getScreenHeight(activity = this@MainActivity)

        when (configuration.defaultTab) {
            0 -> mainPresenter.measureChartHeight(screenHeight = screenHeight.toFloat(),
                    bottomBlockHeight = WindowUtils.convertDpToPixel(80f + 80f + 40f + 3f, applicationContext),
                    topBlockHeight = WindowUtils.convertDpToPixel(72f + 40f + 80f + 40f, applicationContext))
            1 -> mainPresenter.measureChartHeight(screenHeight = screenHeight.toFloat(),
                    bottomBlockHeight = WindowUtils.convertDpToPixel(113f + 113f + 40f, applicationContext),
                    topBlockHeight = WindowUtils.convertDpToPixel(72f + 40f + 80f, applicationContext))
            2 -> mainPresenter.measureChartHeight(screenHeight = screenHeight.toFloat(),
                    bottomBlockHeight = WindowUtils.convertDpToPixel(63f + 63f + 40f, applicationContext),
                    topBlockHeight = WindowUtils.convertDpToPixel(72f + 40f + 80f, applicationContext))
            3 -> mainPresenter.measureChartHeight(screenHeight = screenHeight.toFloat(),
                    bottomBlockHeight = 0f, topBlockHeight = 0f)
        }

        mainPresenter.onChartTimeClick(position = 0, miner = intent?.extras?.getString(Keys.Miner.value).orEmpty(),
                id = intent?.extras?.getInt(Keys.MinerID.value) ?: - 1)
    }

    override fun setupChartHeight(value: Float) {
        val layoutParams = flChart.layoutParams
        layoutParams.height = value.toInt()
        flChart.layoutParams = layoutParams
    }

    override fun setupChartView(position: Int) {
        PaintUtils.setInactiveChartBackground(view = flMainOneHour)
        PaintUtils.setInactiveChartBackground(view = flMainSixHours)
        PaintUtils.setInactiveChartBackground(view = flMainTwelveHours)
        PaintUtils.setInactiveChartBackground(view = flMainOneDay)
        PaintUtils.setInactiveChartBackground(view = flMainThreeDays)
        PaintUtils.setInactiveChartBackground(view = flMainAll)

        txtMainOneHour.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtMainSixHours.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtMainTwelveHours.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtMainOneDay.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtMainThreeDays.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtMainAll.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))

        when (position) {
            0 -> {
                PaintUtils.setChartBackground(view = flMainOneHour)
                txtMainOneHour.setTextColor(Color.WHITE)
            }
            1 -> {
                PaintUtils.setChartBackground(view = flMainSixHours)
                txtMainSixHours.setTextColor(Color.WHITE)
            }
            2 -> {
                PaintUtils.setChartBackground(view = flMainTwelveHours)
                txtMainTwelveHours.setTextColor(Color.WHITE)
            }
            3 -> {
                PaintUtils.setChartBackground(view = flMainOneDay)
                txtMainOneDay.setTextColor(Color.WHITE)
            }
            4 -> {
                PaintUtils.setChartBackground(view = flMainThreeDays)
                txtMainThreeDays.setTextColor(Color.WHITE)
            }
            5 -> {
                PaintUtils.setChartBackground(view = flMainAll)
                txtMainAll.setTextColor(Color.WHITE)
            }
        }
    }

    override fun setupIntroBar(introBarPosition: Float) {
        llMainTopBar.translationY = introBarPosition
    }

    override fun setupTabBar(tabBarPosition: Float) {
        stbMain.translationY = tabBarPosition
    }

    override fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(message: Int) {
        Toast.makeText(applicationContext, getString(message), Toast.LENGTH_LONG).show()
    }

    override fun setupChartData(data: List<Entry>) {
        val xAxis = chartMain.xAxis
        val dataSet = LineDataSet(data, "Template Data")
        dataSet.setDrawFilled(true)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.highLightColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_chart_selection_line
        } else {
            R.color.light_chart_selection_line
        })

        dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext, PaintUtils.getChartGradient())
        dataSet.color = ContextCompat.getColor(applicationContext, PaintUtils.getColorByTheme())

        val lineData = LineData(dataSet)

        chartMain.data = lineData
        chartMain.invalidate()

        val xMax = chartMain.data.getDataSetByIndex(0).xMax
        val xMin = 0f
        xAxis.axisMaximum = xMax
        xAxis.axisMinimum = xMin
        chartMain.invalidate()
    }
}
