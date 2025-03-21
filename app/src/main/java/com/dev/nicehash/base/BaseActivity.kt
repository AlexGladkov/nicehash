package com.dev.nicehash.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.enums.Theme
import android.app.Activity
import android.graphics.Color
import moxy.MvpAppCompatActivity


/**
 * Created by Alex Gladkov on 17.06.18.
 * Base activity for extension with theme support
 */
abstract class BaseActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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

        App.isDark = App.theme == Theme.DarkBlue || App.theme == Theme.DarkPurple ||
                App.theme == Theme.DarkRed || App.theme == Theme.DarkOrange || App.theme == Theme.DarkGreen

        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setStatusBarColor(when {
            App.theme == Theme.DarkPurple -> R.color.dark_status_bar_color
            App.theme == Theme.DarkBlue -> R.color.dark_status_bar_color
            App.theme == Theme.DarkRed -> R.color.dark_status_bar_color
            App.theme == Theme.DarkOrange -> R.color.dark_status_bar_color
            App.theme == Theme.DarkGreen -> R.color.dark_status_bar_color
            else -> R.color.light_status_bar_color
        })
    }

    private fun setStatusBarColor(color: Int) {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.BLACK
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
}