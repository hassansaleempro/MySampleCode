package com.sample.code.util.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.casttypes.app.utils.extensions.isNight
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :  Application()  {



    private fun setupDayNightMode() {
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}