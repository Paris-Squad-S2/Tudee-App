    package com.example.tudeeapp.presentation.screen.base

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch

    open class BaseViewModel<S>(initialState: S) : ViewModel(){

        protected val _uiState = MutableStateFlow(initialState)
        val uiState: StateFlow<S> = _uiState.asStateFlow()

        protected fun launchSafely(
            onLoading: (() -> Unit)? = null,
            onSuccess: suspend () -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                try {
                    onLoading?.let { it() }
                    onSuccess()
                } catch (e: Exception) {
                    onError(e.message ?: "Unexpected error")
                }
            }
        }
    }