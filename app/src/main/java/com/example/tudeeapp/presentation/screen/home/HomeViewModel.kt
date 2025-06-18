package com.example.tudeeapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.TudeeException
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
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
) : ViewModel() {

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
                _homeState.update { it.copy(isLoading = true) }
                taskServices.getAllTasks().collect { tasks ->
                    val tasksUi = tasks.map { it.toTaskUi() }
                    if (tasksUi.isEmpty()) {
                        _homeState.update {
                            it.copy(isTasksEmpty = true, isLoading = false, isSuccess = true)
                        }
                    } else {
                        _homeState.update {
                            it.copy(
                                inProgressTasks = tasksUi.filter { it.status == TaskStatus.IN_PROGRESS },
                                toDoTasks = tasksUi.filter { it.status == TaskStatus.TO_DO },
                                doneTasks = tasksUi.filter { it.status == TaskStatus.DONE },
                                isSuccess = true,
                                isLoading = false,
                                isTasksEmpty = false
                            )
                        }
                    }
                }

            } catch (e: TudeeException) {
                _homeState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }

    }


}

data class HomeViewModeUiState(
    val inProgressTasks: List<TaskUi> = emptyList(),
    val toDoTasks: List<TaskUi> = emptyList(),
    val doneTasks: List<TaskUi> = emptyList(),
    val isTasksEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

data class TaskUi(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val resIconId: Int
)

fun Task.toTaskUi(): TaskUi {
    return TaskUi(
        id = this.id,
        title = title,
        description = description,
        priority = priority,
        status = this.status,
        resIconId = when (priority) {
            TaskPriority.LOW -> R.drawable.ic_trade_down
            TaskPriority.MEDIUM -> android.R.drawable.ic_dialog_alert
            TaskPriority.HIGH -> R.drawable.ic_flag
        }
    )
}