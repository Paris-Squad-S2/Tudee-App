package com.example.tudeeapp.presentation.screen.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
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
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class TaskViewModel(
    private val taskServices: TaskServices
) :ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private var allTasks: List<TaskItemUiState> = emptyList()
    private var currentSelectedDate: LocalDate = getCurrentDate()
    private var currentSelectedStatus: TaskStatusUi = TaskStatusUi.TO_DO

    init {
        loadTasks()
    }

    fun onTabSelected(selectedStatus: TaskStatusUi) {
        currentSelectedStatus = selectedStatus
        updateUiStateWithFilters()
    }

    fun onDateSelected(selectedDate: LocalDate) {
        currentSelectedDate = selectedDate
        updateUiStateWithFilters()
    }

    fun onDatePickerVisibilityChanged() {
        _uiState.value = _uiState.value.copy(
            data = _uiState.value.data.copy(
                showDatePicker = !_uiState.value.data.showDatePicker
            )
        )
    }

    fun goToPreviousMonth() {
        val currentDate = currentSelectedDate.minus(DatePeriod(months = 1))
        onDateSelected(currentDate)
    }

    fun goToNextMonth() {
        val currentDate = currentSelectedDate.plus(DatePeriod(months = 1))
        onDateSelected(currentDate)
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                taskServices.deleteTask(taskId)
                updateUiStateWithFilters()

            }catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                taskServices.getAllTasks().collect{tasks->
                    allTasks = tasks.toTasksUi()
                    updateUiStateWithFilters()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun updateUiStateWithFilters() {
        val filteredTasks = filterTasks(
            tasks = allTasks,
            selectedDate = currentSelectedDate,
            selectedStatus = currentSelectedStatus
        )

        val status = calculateStatusUiList(allTasks, currentSelectedDate, currentSelectedStatus)

        _uiState.value = TaskUiState(
            data = TasksUi(
                calender = currentSelectedDate.createCalendarUi(
                    getCurrentMonthYear(currentSelectedDate),
                    daysOfMonth = formatDailyDate(
                        getAllDaysOfCurrentMonth(currentSelectedDate)
                    )
                ),
                status = status,
                selectedStatus = currentSelectedStatus,
                tasks = filteredTasks,
                showDatePicker = _uiState.value.data.showDatePicker,
                todayIndex = getSelectedDayIndex(currentSelectedDate)
            ),
            isLoading = false,
            errorMessage = null
        )
    }

    private fun filterTasks(
        tasks: List<TaskItemUiState>,
        selectedDate: LocalDate,
        selectedStatus: TaskStatusUi
    ): List<TaskItemUiState> {
        return tasks.filter { task ->
            val matchesDate = task.createdDate == selectedDate
            val matchesStatus = task.status == selectedStatus
            matchesDate && matchesStatus
        }
    }

    private fun calculateStatusUiList(
        tasks: List<TaskItemUiState>,
        selectedDate: LocalDate,
        selectedStatus: TaskStatusUi,
    ): List<StatusUi> {
        val tasksForDate = tasks.filter { it.createdDate == selectedDate }
        val groupedByStatus = tasksForDate.groupingBy { it.status }.eachCount()

        return TaskStatusUi.entries.map { status ->
            StatusUi(
                name = status,
                count = groupedByStatus[status] ?: 0,
                isSelected = status == selectedStatus
            )
        }
    }

    private fun formatDailyDate(daysOfMonth: List<LocalDate>): List<DayUi> {
        return daysOfMonth.map{ date->
            DayUi(
                name = getShortDayName(date),
                num = date.dayOfMonth,
                date = date
            )
        }
    }

    private fun getCurrentDate(): LocalDate {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    private fun getCurrentMonthYear(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMM, yyyy", Locale.getDefault())
        return java.time.LocalDate.of(date.year, date.monthNumber, date.dayOfMonth).format(formatter)
    }

    private fun getShortDayName(date: LocalDate): String {
        val javaDate = java.time.LocalDate.of(date.year, date.monthNumber, date.dayOfMonth)
        return javaDate.format(DateTimeFormatter.ofPattern("EEE", Locale.getDefault()))
    }

    private fun getSelectedDayIndex(date: LocalDate): Int {
        return getAllDaysOfCurrentMonth(date).indexOfFirst { it == currentSelectedDate }
    }

    private fun getAllDaysOfCurrentMonth(date: LocalDate): List<LocalDate> {
        val daysInMonth = date.month.length(isLeapYear(date.year))
        return (1..daysInMonth).map { day -> LocalDate(date.year, date.month, day) }
    }

    private fun isLeapYear(year: Int): Boolean {
        val divisibleBy4 = year % 4 == 0
        val divisibleBy100 = year % 100 == 0
        val divisibleBy400 = year % 400 == 0

        return (divisibleBy4 && !divisibleBy100) || divisibleBy400
    }
}