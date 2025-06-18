package com.example.tudeeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.TudeeException
import kotlinx.coroutines.launch

class MainViewModel(private val taskServices: TaskServices) : ViewModel() {


    // load predefined categories of resources
    fun loadPredefinedCategories() {
        viewModelScope.launch {
            try {
                taskServices.loadPredefinedCategories()
            } catch (e: TudeeException){
                // handle exception
            }
        }
    }
}