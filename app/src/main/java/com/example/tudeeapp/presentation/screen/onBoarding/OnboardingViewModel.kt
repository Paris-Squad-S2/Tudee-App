package com.example.tudeeapp.presentation.screen.onBoarding

import androidx.lifecycle.ViewModel
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class OnboardingViewModel(private val appPreferences: AppPreferences) : ViewModel() {

    private val _state = MutableStateFlow(
        OnboardingState(isCompleted = appPreferences.isOnboardingCompleted())
    )
    val state: StateFlow<OnboardingState> = _state

    fun setOnboardingCompleted() {
            appPreferences.setOnboardingCompleted()
            _state.value = _state.value.copy(isCompleted = true)
    }

    fun updateCurrentPage(index: Int) {
        _state.value = _state.value.copy(currentPage = index)
    }
}