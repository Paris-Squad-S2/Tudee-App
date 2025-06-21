package com.example.tudeeapp.presentation.screen.taskDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.CategoryException
import com.example.tudeeapp.domain.exception.TaskException
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskServices: TaskServices
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailsUiState())
    val uiState: StateFlow<TaskDetailsUiState> = _uiState.asStateFlow()

    val taskId = savedStateHandle.toRoute<Screens.TaskDetails>().taskId

    init {
        loadTaskDetails()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadTaskDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val taskFlow = taskServices.getTaskById(taskId)
                val categoryFlow = taskFlow.flatMapLatest { task ->
                    taskServices.getCategoryById(task.categoryId)
                }
                combine(taskFlow, categoryFlow) { task, category ->
                    Pair(task, category)
                }.collect { (task, category) ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        taskUiState = task.toTaskUiState(),
                        categoryUiState = category.toCategoryUiState()
                    )
                }
            } catch (e: TaskException) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message.toString()
                )
            } catch (e: CategoryException) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message.toString()
                )
            }

        }
    }

    fun onEditTaskStatus(newStatus: TaskStatus) {
        val currentTaskUiState = _uiState.value.taskUiState
        if (currentTaskUiState != null) {
            viewModelScope.launch {
                try {

                    val task = taskServices.getTaskById(currentTaskUiState.id).first()
                    val editTaskState = task.copy(status = newStatus)

                    taskServices.editTask(editTaskState)

                    val editTask = currentTaskUiState.copy(status = newStatus)

                    _uiState.value = _uiState.value.copy(taskUiState = editTask)

                } catch (e: TaskException) {
                    _uiState.value =
                        _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
                }
            }
        }
    }

}