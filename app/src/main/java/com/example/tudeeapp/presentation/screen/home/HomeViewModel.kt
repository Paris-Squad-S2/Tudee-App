package com.example.tudeeapp.presentation.screen.home

import androidx.compose.runtime.MutableState
import com.example.tudeeapp.R
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.TudeeThemeMode
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import com.example.tudeeapp.presentation.screen.home.utils.getToday
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val taskServices: TaskServices,
    private val appPreferences: AppPreferences
) : BaseViewModel<HomeUiState>(HomeUiState()) {


    init {
        loadInitialData()
        getTasksIcons()
        getTasks()
        getSliderState()
    }

    private fun loadInitialData() = launchSafely(
        onSuccess = { _uiState.update { it.copy(isDarkMode = appPreferences.isDarkTheme()) } },
        onError = { errorMessage -> _uiState.update { it.copy(error = errorMessage) } }
    )

    fun onToggledAction(isDarkMode: Boolean,themeMode: MutableState<TudeeThemeMode>) = launchSafely(
        onLoading = { _uiState.update { it.copy(isLoading = true) } },
        onSuccess = {
            themeMode.value = if (isDarkMode) TudeeThemeMode.DARK else TudeeThemeMode.LIGHT
            _uiState.update { it.copy(isLoading = false, isDarkMode = isDarkMode) }
            appPreferences.setDarkTheme(isDarkMode)
                    },
        onError = { errorMessage -> _uiState.update { it.copy(isLoading = false, error = errorMessage) } }
    )

    fun getTasksIcons() = launchSafely(
        onLoading = {_uiState.update { it.copy(isLoading = true) }} ,
        onSuccess = { taskServices.getAllTasks().collect { tasks ->
            tasks.filter(::filterTaskOnToday)
                .let { filteredTasks ->
                    if (filteredTasks.isEmpty()) {
                        _uiState.update {
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

                    val tasksUi = filteredTasks.toTaskUi()
                        .mapIndexed { index, taskUi ->
                            taskUi.copy(
                                categoryIcon = tasksIcons[index],
                                isCategoryPredefined = isCategoryPredefined[index]
                            )
                        }
                    _uiState.update {
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
        }},
        onError = {errorMessage -> _uiState.update { it.copy(error = errorMessage, isLoading = false, isSuccess = false) }}
    )

    fun getTasks() = launchSafely(
        onLoading = {_uiState.update { it.copy(isLoading = true) }},
        onSuccess = { taskServices.getAllTasks().collect { tasks ->
                val tasksUi = tasks
                    .filter(::filterTaskOnToday)
                    .map { it.toTaskUi() }

                if (tasksUi.isEmpty()) {
                    _uiState.update {
                        it.copy(isTasksEmpty = true, isLoading = false, isSuccess = true)
                    }
                } else {
                    _uiState.update {
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
            }},
        onError = { errorMessage -> _uiState.update { it.copy(error =errorMessage, isLoading = false, isSuccess = false) }}
    )


    fun getSliderState() = launchSafely(
        onLoading = {_uiState.update { it.copy(isLoading = true) }},
        onSuccess = {
            taskServices.getAllTasks().collect { tasks ->
                val taskCount = tasks
                    .groupBy { it.status }
                    .mapValues { it.value.size }
                val sliderState = mapTaskCountToSliderState(taskCount)
                _uiState.update { it.copy(sliderState = sliderState, isLoading = false, isSuccess = true) }
            } },
        onError = { errorMessage -> _uiState.update { it.copy(error = errorMessage, isLoading = false, isSuccess = false) }}
    )

    private fun mapTaskCountToSliderState(taskCount: Map<TaskStatus, Int>): SliderUiState {
        val total = taskCount.values.sum()
        val done = taskCount[TaskStatus.DONE] ?: 0
        val toDo = taskCount[TaskStatus.TO_DO] ?: 0

        return when {
            total == 0 -> SliderUiState.NothingState
            done > 0 && done == total -> SliderUiState.GoodState(done, total)
            done == 0 -> SliderUiState.ZeroProgressState(toDo, total)
            else -> SliderUiState.StayWorkingState(done, total)
        }
    }

    private fun filterTaskOnToday(task: Task): Boolean = task.createdDate == getToday().date
    private fun List<Task>.toTaskUi(): List<TaskUiState> = this.map { it.toTaskUi() }
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

    fun onFloatingActionButtonClick() {
        navigate(Destinations.TaskManagement(selectedDate = getToday().date.toString()))
    }

    fun onTasksCountClick(tasksTitle: String) {
        navigate(Destinations.Tasks(tasksTitle))
    }

    fun onTaskClick(taskId: Long) {
        navigate(Destinations.TaskDetails(taskId))
    }

}





