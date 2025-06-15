package com.example.tudeeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashViewModel(private val appInitializer: AppInitializer): ViewModel() {
    init {
            appInitializer.initializeApp()
    }
}