package com.example.tudeeapp.data.source.local.sharedPreferences

import android.content.Context
import androidx.core.content.edit

class AppPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean = prefs.getBoolean("first_launch", true)

    fun setFirstLaunchDone() {
        prefs.edit { putBoolean("first_launch", false) }
    }
}