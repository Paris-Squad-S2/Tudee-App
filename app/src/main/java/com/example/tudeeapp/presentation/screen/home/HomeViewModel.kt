package com.example.tudeeapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.TudeeException
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskServices: TaskServices
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeViewModeUiState())
    val homeState = _homeState
        .onStart { getTasks() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeViewModeUiState()
        )

    fun getTasks() {
        viewModelScope.launch(IO) {
            try {
                taskServices.getAllTasks().collect { tasks ->
                    if (tasks.isEmpty()) {
                        _homeState.update {
                            it.copy(isTasksEmpty = true)
                        }
                    }else {
                        _homeState.update {
                            it.copy(
                                inProgressTasks = tasks.filter { it.status == TaskStatus.IN_PROGRESS },
                                toDoTasks = tasks.filter { it.status == TaskStatus.TO_DO },
                                doneTasks = tasks.filter { it.status == TaskStatus.DONE },
                                isTasksEmpty = false
                            )
                        }
                    }
                }

            } catch (e: TudeeException) {
                _homeState.update {
                    it.copy(error = e.message)
                }
            }
        }

    }


}

data class HomeViewModeUiState(
    val inProgressTasks: List<Task> = emptyList(),
    val toDoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val isTasksEmpty: Boolean = true,
    val error: String? = null
)