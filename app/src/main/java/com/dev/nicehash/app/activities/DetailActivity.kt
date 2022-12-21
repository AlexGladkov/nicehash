package com.dev.nicehash.app.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.arellomobile.mvp.presenter.InjectPresenter
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.presenters.DetailPresenter
import com.dev.nicehash.app.views.DetailView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Balance
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.Theme
import com.dev.nicehash.helpers.PaintUtils
import com.dev.nicehash.helpers.WindowUtils
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Alex Gladkov on 30.07.18
 * Activity for balance detail view
 */
class DetailActivity: BaseActivity(), DetailView {
    private val TAG = DetailActivity::class.java.simpleName

    @InjectPresenter lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val balance = intent.extras?.get(Keys.Balance.value) as? Balance
        if (balance == null) onBackPressed()

        txtDetailTitle.text = balance?.title.orEmpty()
        btnDetailBack.setOnClickListener { onBackPressed() }

        tuneChart()
        detailPresenter.onChartTimeClick(position = 0)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    private fun tuneChart() {
        chartDetail.fitScreen()
        chartDetail.description.isEnabled = false
        chartDetail.legend.isEnabled = false
        chartDetail.setViewPortOffsets(0f, 0f, 0f, 0f)
        chartDetail.isDoubleTapToZoomEnabled = false

        val xAxis = chartDetail.xAxis
        xAxis.setDrawLimitLinesBehindData(true);
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

        chartDetail.axisRight.isEnabled = false
        chartDetail.axisRight.spaceBottom = 0f
        chartDetail.xAxis.isEnabled = false

        chartDetail.axisLeft.isEnabled = true
        chartDetail.axisLeft.addLimitLine(averageLine)
        chartDetail.axisLeft.setLabelCount(9, true)
        chartDetail.axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        chartDetail.axisLeft.gridColor = if (App.isDark) {
            Color.parseColor("#1AFFFFFF")
        } else {
            Color.parseColor("#0D000000")
        }
        chartDetail.axisLeft.gridLineWidth = 0.5f
        chartDetail.axisLeft.textSize = 10f
        chartDetail.axisLeft.setDrawTopYLabelEntry(false)
        chartDetail.axisLeft.setDrawZeroLine(false)
        chartDetail.axisLeft.valueFormatter = IAxisValueFormatter { value, _ ->
            if (value < 0.00001f) {
                return@IAxisValueFormatter ""
            }

            String.format("%.05f", value)
        }
        chartDetail.axisLeft.axisLineColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_background_secondary
        } else {
            R.color.light_background_secondary
        })

        chartDetail.axisLeft.textColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_text_color_primary
        } else {
            R.color.light_text_color_primary
        })
    }

    // MARK: - View implementation
    override fun setupChartView(position: Int) {
        PaintUtils.setInactiveChartBackground(view = flDetailOneHour)
        PaintUtils.setInactiveChartBackground(view = flDetailSixHours)
        PaintUtils.setInactiveChartBackground(view = flDetailTwelveHours)
        PaintUtils.setInactiveChartBackground(view = flDetailOneDay)
        PaintUtils.setInactiveChartBackground(view = flDetailThreeDays)
        PaintUtils.setInactiveChartBackground(view = flDetailAll)

        txtDetailOneHour.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtDetailSixHours.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtDetailTwelveHours.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtDetailOneDay.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtDetailThreeDays.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        txtDetailAll.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))

        when (position) {
            0 -> {
                PaintUtils.setChartBackground(view = flDetailOneHour)
                txtDetailOneHour.setTextColor(Color.WHITE)
            }
            1 -> {
                PaintUtils.setChartBackground(view = flDetailSixHours)
                txtDetailSixHours.setTextColor(Color.WHITE)
            }
            2 -> {
                PaintUtils.setChartBackground(view = flDetailTwelveHours)
                txtDetailTwelveHours.setTextColor(Color.WHITE)
            }
            3 -> {
                PaintUtils.setChartBackground(view = flDetailOneDay)
                txtDetailOneDay.setTextColor(Color.WHITE)
            }
            4 -> {
                PaintUtils.setChartBackground(view = flDetailThreeDays)
                txtDetailThreeDays.setTextColor(Color.WHITE)
            }
            5 -> {
                PaintUtils.setChartBackground(view = flDetailAll)
                txtDetailAll.setTextColor(Color.WHITE)
            }
        }
    }

    override fun setupChartData(data: List<Entry>) {
        val xAxis = chartDetail.xAxis
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

        dataSet.fillDrawable = ContextCompat.getDrawable(applicationContext, when (App.theme) {
            Theme.LightBlue -> R.drawable.light_chart_gradient_blue
            Theme.LightGreen -> R.drawable.light_chart_gradient_green
            Theme.LightOrange -> R.drawable.light_chart_gradient_orange
            Theme.LightRed -> R.drawable.light_chart_gradient_red
            Theme.LightPurple -> R.drawable.light_chart_gradient_purple
            Theme.DarkPurple -> R.drawable.dark_chart_gradient_purple
            Theme.DarkRed -> R.drawable.dark_chart_gradient_red
            Theme.DarkOrange -> R.drawable.dark_chart_gradient_orange
            Theme.DarkGreen -> R.drawable.dark_chart_gradient_green
            Theme.DarkBlue -> R.drawable.dark_chart_gradient_blue
        })

        dataSet.color = ContextCompat.getColor(applicationContext, PaintUtils.getColorByTheme())

        val lineData = LineData(dataSet)

        chartDetail.data = lineData
        chartDetail.invalidate()

        val xMax = chartDetail.data.getDataSetByIndex(0).xMax
        val xMin = 0f
        xAxis.axisMaximum = xMax
        xAxis.axisMinimum = xMin
        chartDetail.invalidate()
    }
}