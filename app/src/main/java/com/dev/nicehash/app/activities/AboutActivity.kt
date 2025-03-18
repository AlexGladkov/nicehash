package com.dev.nicehash.app.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.dev.nicehash.R
import com.dev.nicehash.base.BaseActivity
import android.content.pm.PackageManager
import android.R.attr.versionName
//import com.google.android.gms.common.util.ClientLibraryUtils.getPackageInfo
import android.content.pm.PackageInfo



/**
 * Created by Alex Gladkov on 24.06.18.
 * Activity for about information
 */
class AboutActivity: BaseActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

//        imgAboutBack.setOnClickListener { onBackPressed() }
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
//            txtAboutVersion.text = "${getString(R.string.app_version)} ${pInfo.versionName}, " +
//                    "${getString(R.string.app_build).toLowerCase()} ${pInfo.versionCode}"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}