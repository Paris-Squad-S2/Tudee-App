package com.example.tudeeapp.presentation.common.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SnackBarState {
    var isVisible by mutableStateOf(false)
        private set

    var message by mutableStateOf("")
    var isSuccess by mutableStateOf(true)
    var durationMillis by mutableIntStateOf(3000)

    fun show(message: String, isSuccess: Boolean = true, duration: Int = 3000) {
        this.message = message
        this.isSuccess = isSuccess
        this.durationMillis = duration
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }
}