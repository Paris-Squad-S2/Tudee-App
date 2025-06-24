package com.example.tudeeapp.presentation.screen.onBoarding

import androidx.navigation.NavOptions
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.base.BaseViewModel


class OnBoardingViewModel(
    private val appPreferences: AppPreferences
) : BaseViewModel<OnBoardingUIState>(
    OnBoardingUIState(isCompleted = appPreferences.isOnboardingCompleted())
) {
    fun navigateToHome() {
        navigate(Destinations.Home,NavOptions.Builder()
            .setPopUpTo(Destinations.OnBoarding, inclusive = true)
            .build())
    }

    fun setOnboardingCompleted() {
        appPreferences.setOnboardingCompleted()
        _uiState.value = _uiState.value.copy(isCompleted = true)
    }

    fun updateCurrentPage(index: Int) {
        _uiState.value = _uiState.value.copy(currentPage = index)
    }
}