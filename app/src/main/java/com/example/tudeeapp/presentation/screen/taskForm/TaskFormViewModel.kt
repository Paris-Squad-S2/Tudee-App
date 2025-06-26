package com.example.tudeeapp.presentation.screen.taskForm

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import com.example.tudeeapp.presentation.screen.home.utils.getToday
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.random.Random

class TaskFormViewModel(
    private val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskFormUiState>(TaskFormUiState(isLoading = true)) , InteractionListener {

    val taskId = savedStateHandle.toRoute<Destinations.TaskManagement>().taskId
    private val selectedDate = savedStateHandle.toRoute<Destinations.TaskManagement>().selectedDate

    init {
        _uiState.update {
            it.copy(isEditMode = taskId != null, selectedDate = selectedDate)
        }
        getAllCategories()
    }

    override fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    override fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    override fun onDateClicked(isVisible: Boolean) {
        _uiState.update { it.copy(isDatePickerVisible = isVisible) }
    }

    override fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date.toString(), isDatePickerVisible = false) }
    }

    override fun onPrioritySelected(priorityUiState: TaskPriorityUiState) {
        _uiState.update { currentState ->
            currentState.copy(selectedPriority = priorityUiState)
        }
    }

    override fun popBackStack() { navigateUp() }

    override fun onCategorySelected(categoryId: Long) {
        _uiState.update { currentState ->
            val isCurrentlySelected = currentState.selectedCategoryId == categoryId
            val updatedCategories = currentState.categories.map { category ->
                category.copy(isSelected = !isCurrentlySelected && category.id == categoryId)
            }
            currentState.copy(
                categories = updatedCategories,
                selectedCategoryId = if (isCurrentlySelected) null else categoryId
            )
        }
    }

    override fun onActionButtonClicked() {
        launchSafely(
            onLoading = { _uiState.update { it.copy(isLoading = true) } },
            onSuccess = {
                val currentState = _uiState.value
                val createdDate = if (currentState.selectedDate.contains("T")) {
                    LocalDateTime.parse(currentState.selectedDate)
                } else {
                    LocalDateTime.parse(currentState.selectedDate + "T" + getToday().time.toString())
                }
                val task = Task(
                    id = taskId ?: Random.nextLong(1L, Long.MAX_VALUE),
                    title = currentState.title,
                    description = currentState.description,
                    priority = currentState.selectedPriority.toTaskPriority() ?: TaskPriority.LOW,
                    status = if (currentState.isEditMode) currentState.taskStatus else TaskStatus.TO_DO,
                    createdDate =createdDate,
                    categoryId = currentState.selectedCategoryId ?: 0L
                )
                if (currentState.isEditMode) taskServices.editTask(task) else taskServices.addTask(task)
                _uiState.update { it.copy(isLoading = false, isTaskSaved = true) }
            },
            onError = {
                handleException()
            }
        )
    }

    private fun getAllCategories() {
        launchSafely(
            onLoading = { _uiState.update { it.copy(isLoading = true) } },
            onSuccess = {
                taskServices.getAllCategories().collect { categories ->
                    _uiState.update {
                        it.copy(
                            categories = categories.map { category -> category.toCategoryState() },
                            isLoading = false
                        )
                    }
                    taskId?.let { getTaskById(it) }
                }
            },
            onError = { handleException() }
        )
    }

    private fun getTaskById(id: Long) {
        launchSafely(
            onLoading = { _uiState.update { it.copy(isLoading = true) } },
            onSuccess = {
                taskServices.getTaskById(id).collect { task ->
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            selectedDate = task.createdDate.toString(),
                            selectedPriority = TaskPriorityUiState.fromDomain(task.priority),
                            selectedCategoryId = task.categoryId,
                            taskStatus = task.status,
                            isLoading = false,
                            categories = it.categories.map { category ->
                                category.copy(isSelected = category.id == task.categoryId)
                            })
                    }
                }
            },
            onError = { handleException() }
        )
    }

    private fun handleException() {
        _uiState.update {
            it.copy(
                isLoading = false, error = "There was an error processing your request. Please try again later."
            )
        }
    }
}