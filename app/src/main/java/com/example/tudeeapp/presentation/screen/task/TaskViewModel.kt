package com.example.tudeeapp.presentation.screen.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.presentation.screen.task.mapper.toTasksUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class TaskViewModel(
    private val taskServices: TaskServices
) :ViewModel() {
    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.isLoading)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val today: LocalDate = getCurrentDate()

    init {
        loadInitialState()
    }

    private fun loadInitialState() {
        val initialState = TasksUi(
            selectedDate = today,
            daysOfMonth = getAllDaysOfCurrentMonth(today),
            currentMonthYear = getCurrentMonthYear(today),
            todayIndex = getTodayIndex(today)
        )
        _uiState.value = TaskUiState.success(initialState)

        getAllTasks()
    }

    fun onTabSelected(status: TaskStatusUi) {
        updateUiState { it.copy(selectedStatus = status) }
        getAllTasks()
    }

    fun onDateSelected(date: LocalDate) {
        updateUiState {
            it.copy(
                selectedDate = date,
                daysOfMonth = getAllDaysOfCurrentMonth(date),
                currentMonthYear = getCurrentMonthYear(date),
                todayIndex = getTodayIndex(date)
            )
        }
        getAllTasks()
    }

    fun onDatePickerVisibilityChanged(show: Boolean) {
        updateUiState { it.copy(showDatePicker = show) }
    }

    fun goToPreviousMonth() {
        val currentDate = currentUiData().selectedDate.minus(DatePeriod(months = 1))
        onDateSelected(currentDate)
    }

    fun goToNextMonth() {
        val currentDate = currentUiData().selectedDate.plus(DatePeriod(months = 1))
        onDateSelected(currentDate)
    }

    fun formatDayName(date: LocalDate): String {
        return date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            try {
                taskServices.getAllTasks().collect { allTasks ->
                    val selectedDate = getSelectedDate()
                    val selectedStatus = getSelectedStatus()

                    val filteredTasks = allTasks
                        .toTasksUi()
                        .filter { it.status == selectedStatus && it.createdDate == selectedDate }

                    val daysOfMonth = getAllDaysOfCurrentMonth(selectedDate)
                    val currentMonthYear = getCurrentMonthYear(selectedDate)
                    val todayIndex = daysOfMonth.indexOfFirst { it == selectedDate }

                    _uiState.value = TaskUiState.success(
                        TasksUi(
                            tasks = filteredTasks,
                            selectedStatus = selectedStatus,
                            selectedDate = selectedDate,
                            daysOfMonth = daysOfMonth,
                            currentMonthYear = currentMonthYear,
                            todayIndex = todayIndex,
                            showDatePicker = false,
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = TaskUiState.error(e.message ?: "Unknown error")
            }
        }
    }

    private fun getSelectedDate(): LocalDate {
        return (uiState.value as? TaskUiState.success)?.data?.selectedDate
            ?: getCurrentDate()
    }

    private fun getSelectedStatus(): TaskStatusUi {
        return (uiState.value as? TaskUiState.success)?.data?.selectedStatus
            ?: TaskStatusUi.TO_DO
    }

    private fun updateUiState(transform: (TasksUi) -> TasksUi) {
        val current = currentUiDataOrNull() ?: return
        _uiState.value = TaskUiState.success(transform(current))
    }

    private fun currentUiDataOrNull(): TasksUi? {
        return (_uiState.value as? TaskUiState.success)?.data
    }

    private fun currentUiData(): TasksUi {
        return currentUiDataOrNull() ?: TasksUi()
    }

    private fun getCurrentDate(): LocalDate {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    private fun getCurrentMonthYear(date: LocalDate): String {
        return "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)}, ${date.year}"
    }

    private fun getTodayIndex(date: LocalDate): Int {
        return getAllDaysOfCurrentMonth(date).indexOfFirst { it == today }
    }

    private fun getAllDaysOfCurrentMonth(date: LocalDate): List<LocalDate> {
        val daysInMonth = date.month.length(isLeapYear(date.year))
        return (1..daysInMonth).map { day -> LocalDate(date.year, date.month, day) }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}
