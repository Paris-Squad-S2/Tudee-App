package com.example.tudeeapp.presentation.screen.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class OnboardingViewModel(
    private val onboardingPreferences: OnboardingPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(
        OnboardingState(isCompleted = onboardingPreferences.isOnboardingCompleted())
    )
    val state: StateFlow<OnboardingState> = _state

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            onboardingPreferences.setOnboardingCompleted()
            _state.value = _state.value.copy(isCompleted = true)
        }
    }

    fun updateCurrentPage(index: Int) {
        _state.value = _state.value.copy(currentPage = index)
    }
}