package com.example.tudeeapp.presentation.screen.taskDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

class TaskDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskServices: TaskServices
) : BaseViewModel<TaskDetailsUiState>(TaskDetailsUiState()) {

    val taskId = savedStateHandle.toRoute<Destinations.TaskDetails>().taskId

    init {
        loadTaskDetails()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadTaskDetails() {
        launchSafely(
            onLoading = { _uiState.update { it.copy(isLoading = true) } },
            onSuccess = {
                val taskFlow = taskServices.getTaskById(taskId)
                val categoryFlow = taskFlow.flatMapLatest { task ->
                    taskServices.getCategoryById(task.categoryId)
                }
                combine(taskFlow, categoryFlow) { task, category ->
                    Pair(task, category)
                }.collect { (task, category) ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            taskUiState = task.toTaskUiState(),
                            categoryUiState = category.toCategoryUiState()
                        )
                    }
                }
            },
            onError = { errorMessage ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        )
    }

    fun onEditTaskStatus(newStatus: TaskStatus) {
        val currentTaskUiState = _uiState.value.taskUiState
        if (currentTaskUiState != null) {
            launchSafely(
                onSuccess = {
                    val task = taskServices.getTaskById(currentTaskUiState.id).first()
                    val editTaskState = task.copy(status = newStatus)
                    taskServices.editTask(editTaskState)
                    navigateUp()
                },
                onError = {
                    _uiState.value =
                        _uiState.value.copy(errorMessage = it)
                    navigateUp()
                }
            )
        }
    }

    fun onDismiss() {
        navigateUp()
    }

    fun onEditTaskClick() {
        val taskUiState = _uiState.value.taskUiState ?: return
        navigate(
            Destinations.TaskManagement(
                taskUiState.id,
                taskUiState.createdDate.toString()
            )
        )
    }

}