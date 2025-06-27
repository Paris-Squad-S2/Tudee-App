package com.example.tudeeapp.data.source.local.sharedPreferences

import android.content.Context
import androidx.core.content.edit

class AppPreferences(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isAppLaunchForFirstTime(): Boolean = prefs.getBoolean(KEY_FIRST_LAUNCH, true)

    fun setAppLaunchIsDone() { prefs.edit { putBoolean(KEY_FIRST_LAUNCH, false) } }

    fun isOnboardingCompleted(): Boolean = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    fun setOnboardingCompleted(completed: Boolean = true) {
        prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, completed) }
    }

    fun isDarkTheme(): Int = prefs.getInt(KEY_DARK_THEME, 2)

    fun setDarkTheme(isDark: Boolean) {
        prefs.edit { putInt(KEY_DARK_THEME, if (isDark) 1 else 0) }
    }

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_DARK_THEME = "theme"
    }
}