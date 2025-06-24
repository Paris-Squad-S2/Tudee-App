package com.example.tudeeapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.R
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.TudeeException
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.screen.home.utils.getToday
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskServices: TaskServices,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState
        .onStart {
            getTasksIcons()
            getTasks()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _homeState.update { it.copy(isDarkMode = appPreferences.isDarkTheme()) }
        }
    }

    fun onToggledAction(isDarkMode: Boolean) {
        _homeState.update { it.copy(isDarkMode = isDarkMode) }
        viewModelScope.launch { appPreferences.setDarkTheme(isDarkMode) }
    }

    fun getTasksIcons() {
        viewModelScope.launch(IO) {
            val today = getToday()
            try {
                taskServices.getAllTasks().collect { tasks ->
                    tasks.filter { it.createdDate == today.date }
                        .also { filteredTasks ->
                            if (filteredTasks.isEmpty()) {
                                _homeState.update {
                                    it.copy(
                                        isTasksEmpty = true,
                                        isLoading = false,
                                        isSuccess = true
                                    )
                                }
                                return@collect
                            }
                            val tasksIcons =
                                filteredTasks.map {
                                    taskServices.getCategoryById(it.categoryId).first().imageUri
                                }

                            val isCategoryPredefined = filteredTasks.map {
                                taskServices.getCategoryById(it.categoryId).first().isPredefined
                            }

                            val tasksUi = filteredTasks.map { it.toTaskUi() }
                                .mapIndexed { index, taskUi ->
                                    taskUi.copy(
                                        categoryIcon = tasksIcons[index],
                                        isCategoryPredefined = isCategoryPredefined[index]
                                    )
                                }
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
            } catch (e: Exception) {
                _homeState.update {
                    it.copy(error = e.message, isLoading = false, isSuccess = false)
                }
            }
        }
    }

    fun getTasks() {
        val today = getToday()
        viewModelScope.launch(IO) {
            try {
                _homeState.update { it.copy(isLoading = true) }
                taskServices.getAllTasks().collect { tasks ->
                    val tasksUi = tasks
                        .filter { it.createdDate == today.date }
                        .map { it.toTaskUi() }


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
                    it.copy(error = e.message, isLoading = false, isSuccess = false)
                }
            }
        }

    }


    private fun Task.toTaskUi(): TaskUiState {
        return TaskUiState(
            id = this.id,
            title = title,
            description = description,
            priority = priority,
            priorityResIcon = when (priority) {
                TaskPriority.LOW -> R.drawable.ic_trade_down
                TaskPriority.MEDIUM -> android.R.drawable.ic_dialog_alert
                TaskPriority.HIGH -> R.drawable.ic_flag
            },
            status = this.status,
            categoryIcon = "",
            isCategoryPredefined = false
        )
    }

}





