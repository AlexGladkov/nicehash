package com.dev.nicehash.app.activities

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.app.presenters.DetailPresenter
import com.dev.nicehash.app.views.DetailView
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.databinding.ActivityDetailBinding
import com.dev.nicehash.domain.models.Balance
import com.dev.nicehash.enums.Keys
import com.dev.nicehash.enums.Theme
import com.dev.nicehash.helpers.PaintUtils
import com.dev.nicehash.helpers.WindowUtils
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import moxy.presenter.InjectPresenter

/**
 * Created by Alex Gladkov on 30.07.18
 * Activity for balance detail view
 */
class DetailActivity: BaseActivity(), DetailView {
    private val TAG = DetailActivity::class.java.simpleName

    @InjectPresenter
    lateinit var detailPresenter: DetailPresenter

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val balance = intent.extras?.get(Keys.Balance.value) as? Balance
        if (balance == null) onBackPressed()

        binding.txtDetailTitle.text = balance?.title.orEmpty()
        binding.btnDetailBack.setOnClickListener { onBackPressed() }

        tuneChart()
        detailPresenter.onChartTimeClick(position = 0)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    private fun tuneChart() {
        binding.chartDetail.fitScreen()
        binding.chartDetail.description.isEnabled = false
        binding.chartDetail.legend.isEnabled = false
        binding.chartDetail.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding.chartDetail.isDoubleTapToZoomEnabled = false

        val xAxis = binding.chartDetail.xAxis
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

        binding.chartDetail.axisRight.isEnabled = false
        binding.chartDetail.axisRight.spaceBottom = 0f
        binding.chartDetail.xAxis.isEnabled = false

        binding.chartDetail.axisLeft.isEnabled = true
        binding.chartDetail.axisLeft.addLimitLine(averageLine)
        binding.chartDetail.axisLeft.setLabelCount(9, true)
        binding.chartDetail.axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        binding.chartDetail.axisLeft.gridColor = if (App.isDark) {
            Color.parseColor("#1AFFFFFF")
        } else {
            Color.parseColor("#0D000000")
        }
        binding.chartDetail.axisLeft.gridLineWidth = 0.5f
        binding.chartDetail.axisLeft.textSize = 10f
        binding.chartDetail.axisLeft.setDrawTopYLabelEntry(false)
        binding.chartDetail.axisLeft.setDrawZeroLine(false)
        binding.chartDetail.axisLeft.valueFormatter = IAxisValueFormatter { value, _ ->
            if (value < 0.00001f) {
                return@IAxisValueFormatter ""
            }

            String.format("%.05f", value)
        }
        binding.chartDetail.axisLeft.axisLineColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_background_secondary
        } else {
            R.color.light_background_secondary
        })

        binding.chartDetail.axisLeft.textColor = ContextCompat.getColor(applicationContext, if (App.isDark) {
            R.color.dark_text_color_primary
        } else {
            R.color.light_text_color_primary
        })
    }

    // MARK: - View implementation
    override fun setupChartView(position: Int) {
        PaintUtils.setInactiveChartBackground(view = binding.flDetailOneHour)
        PaintUtils.setInactiveChartBackground(view = binding.flDetailSixHours)
        PaintUtils.setInactiveChartBackground(view = binding.flDetailTwelveHours)
        PaintUtils.setInactiveChartBackground(view = binding.flDetailOneDay)
        PaintUtils.setInactiveChartBackground(view = binding.flDetailThreeDays)
        PaintUtils.setInactiveChartBackground(view = binding.flDetailAll)

        binding.txtDetailOneHour.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        binding.txtDetailSixHours.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        binding.txtDetailTwelveHours.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        binding.txtDetailOneDay.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        binding.txtDetailThreeDays.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))
        binding.txtDetailAll.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color_hint))

        when (position) {
            0 -> {
                PaintUtils.setChartBackground(view = binding.flDetailOneHour)
                binding.txtDetailOneHour.setTextColor(Color.WHITE)
            }
            1 -> {
                PaintUtils.setChartBackground(view = binding.flDetailSixHours)
                binding.txtDetailSixHours.setTextColor(Color.WHITE)
            }
            2 -> {
                PaintUtils.setChartBackground(view = binding.flDetailTwelveHours)
                binding.txtDetailTwelveHours.setTextColor(Color.WHITE)
            }
            3 -> {
                PaintUtils.setChartBackground(view = binding.flDetailOneDay)
                binding.txtDetailOneDay.setTextColor(Color.WHITE)
            }
            4 -> {
                PaintUtils.setChartBackground(view = binding.flDetailThreeDays)
                binding.txtDetailThreeDays.setTextColor(Color.WHITE)
            }
            5 -> {
                PaintUtils.setChartBackground(view = binding.flDetailAll)
                binding.txtDetailAll.setTextColor(Color.WHITE)
            }
        }
    }

    override fun setupChartData(data: List<Entry>) {
        val xAxis = binding.chartDetail.xAxis
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

        binding.chartDetail.data = lineData
        binding.chartDetail.invalidate()

        val xMax = binding.chartDetail.data.getDataSetByIndex(0).xMax
        val xMin = 0f
        xAxis.axisMaximum = xMax
        xAxis.axisMinimum = xMin
        binding.chartDetail.invalidate()
    }
}