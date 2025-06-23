package com.example.tudeeapp.presentation.screen.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.task.TaskStatusUi.Companion.fromNameOrDefault
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormatSymbols
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class TasksViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskServices: TaskServices
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private var allTasks: List<TaskItemUiState> = emptyList()
    private var currentSelectedDate: LocalDate = getCurrentDate()
    private var currentSelectedStatus: TaskStatusUi = fromNameOrDefault(
        savedStateHandle.toRoute<Destinations.Tasks>().tasksStatus
    )

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

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                taskServices.getAllTasks().collect { tasks ->
                    val tasksUi = tasks.map { task ->
                        val category = taskServices.getCategoryById(task.categoryId).first()
                        task.toTaskUiState().copy(
                            category = category.toCategoryUi(),
                        )
                    }

                    allTasks = tasksUi
                    updateUiStateWithFilters()
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error occurred"
                    )
                }
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

        _uiState.update { it.copy(
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
        ) }
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
        return daysOfMonth.map { date ->
            DayUi(
                name = getShortDayName(date),
                num = date.dayOfMonth,
                date = date
            )
        }
    }


    private fun getCurrentDate(): LocalDate {
        return Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    }

    private fun getCurrentMonthYear(date: LocalDate): String {
        val month = DateFormatSymbols(Locale.getDefault()).shortMonths[date.monthNumber -1]
        return "$month, ${date.year}"
    }

    private fun getShortDayName(date: LocalDate): String {
        val dayOfWeek = date.dayOfWeek.value
        val androidWeekdayIndex = if (dayOfWeek == 7) 1 else dayOfWeek + 1
        val day = DateFormatSymbols(Locale.getDefault()).shortWeekdays[androidWeekdayIndex]
        return day
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