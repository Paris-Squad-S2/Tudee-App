package com.example.tudeeapp.presentation.screen.onBoarding

import android.content.Context
import androidx.core.content.edit

class OnboardingPreferences(private val context: Context) {

    fun isOnboardingCompleted(): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_COMPLETED, false)
    }

    fun setOnboardingCompleted() {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit {
                putBoolean(KEY_COMPLETED, true)
            }
    }

    companion object {
        private const val PREF_NAME = "onboarding"
        private const val KEY_COMPLETED = "completed"
    }

}