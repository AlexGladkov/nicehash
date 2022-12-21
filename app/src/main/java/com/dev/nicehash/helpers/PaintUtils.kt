package com.dev.nicehash.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.FrameLayout
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.enums.Theme

/**
 * Created by Alex Gladkov on 21.07.18.
 * Paint view by theme
 */
class PaintUtils {

    companion object {
        fun setChartBackground(view: View) {
            view.background = ContextCompat.getDrawable(view.context, when (App.theme) {
                Theme.DarkOrange -> R.drawable.dark_orange_chart_time_background
                Theme.LightOrange -> R.drawable.light_orange_chart_time_background
                Theme.DarkBlue -> R.drawable.dark_blue_chart_time_background
                Theme.LightBlue -> R.drawable.light_blue_chart_time_background
                Theme.DarkGreen -> R.drawable.dark_green_chart_time_background
                Theme.LightGreen -> R.drawable.light_green_chart_time_background
                Theme.DarkRed -> R.drawable.dark_red_chart_time_background
                Theme.LightRed -> R.drawable.light_red_chart_time_background
                Theme.DarkPurple -> R.drawable.dark_purple_chart_time_background
                Theme.LightPurple -> R.drawable.light_purple_chart_time_background
            })
        }

        fun setInactiveChartBackground(view: View) {
            view.background = ContextCompat.getDrawable(view.context, when (App.isDark) {
                true -> R.drawable.dark_chart_time_background
                false -> R.drawable.light_chart_time_background
            })
        }

        fun getChartGradient(): Int {
            return when (App.theme) {
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
            }
        }

        fun getColorByTheme(): Int {
            return when (App.theme) {
                Theme.DarkOrange -> R.color.dark_orange_color_primary
                Theme.LightOrange -> R.color.light_orange_color_primary
                Theme.DarkBlue -> R.color.dark_blue_color_primary
                Theme.LightBlue -> R.color.light_blue_color_primary
                Theme.DarkGreen -> R.color.dark_green_color_primary
                Theme.LightGreen -> R.color.light_green_color_primary
                Theme.DarkRed -> R.color.dark_red_color_primary
                Theme.LightRed -> R.color.light_red_color_primary
                Theme.DarkPurple -> R.color.dark_purple_color_primary
                Theme.LightPurple -> R.color.light_purple_color_primary
            }
        }
    }
}