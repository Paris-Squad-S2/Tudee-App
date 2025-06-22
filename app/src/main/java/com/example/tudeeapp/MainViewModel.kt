package com.example.tudeeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val taskServices: TaskServices) : ViewModel() {

    private val _mainState = MutableStateFlow(MainUiState())
    val mainState = _mainState.asStateFlow()

    init {
        loadPredefinedCategories()
    }

    private fun loadPredefinedCategories(){
        viewModelScope.launch(IO) {
           try {
               taskServices.loadPredefinedCategories()
              _mainState.update {it.copy(isSuccess = true)}
           }catch (e: Exception){
               _mainState.update {
                   it.copy(error = e.message, isSuccess = false)
               }
           }
        }
    }
}

data class MainUiState(
    val isSuccess: Boolean = false,
    val error: String? = null
)