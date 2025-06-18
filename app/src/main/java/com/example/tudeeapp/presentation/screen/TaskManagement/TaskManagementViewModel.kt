package com.example.tudeeapp.presentation.screen.TaskManagement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TaskManagementViewModel(
    private val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val taskId = savedStateHandle.toRoute<Screens.TaskManagement>().taskId

    private val _state = MutableStateFlow(
        TaskManagementUiState(
            selectedPriority = TaskPriorityUiState.NONE, categories = emptyList(),isEditMode = taskId != null)
    )

    val state = _state.asStateFlow()

    init {
        getAllCategories()
       taskId?.let {
           getTaskById(it)
       }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            taskServices.getAllCategories().collect { categories ->
                _state.update {
                    it.copy(categories = categories.map { category -> category.toCategoryState() })
                }
            }
        }
    }

    private fun getTaskById(id:Int){
        //todo
    }

    fun onTitleChange(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun onDescriptionChange(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onDateClicked(isVisible: Boolean) {
        _state.update { it.copy(isDatePickerVisible = isVisible) }
    }

    fun onDateSelected(date: LocalDate) {
        _state.update { it.copy(selectedDate = date.toString(), isDatePickerVisible = false) }
    }

    fun onPrioritySelected(priorityUiState: TaskPriorityUiState) {
        _state.update { currentState ->
            currentState.copy(selectedPriority = priorityUiState)
        }
    }

    fun onCategorySelected(categoryId: Int) {
        _state.update { currentState ->
            val isCurrentlySelected = currentState.selectedCategoryId == categoryId
            val updatedCategories = currentState.categories.mapIndexed { index, category ->
                category.copy(isSelected = !isCurrentlySelected && index == categoryId)
            }
            currentState.copy(
                categories = updatedCategories,
                selectedCategoryId = if (isCurrentlySelected) null else categoryId
            )
        }
    }

    fun onActionButtonClicked() {
        viewModelScope.launch {
            try {
                val currentState = _state.value

                val createDate: LocalDate = LocalDate.parse(currentState.selectedDate)

                taskServices.addTask(
                    Task(
                        title = currentState.title,
                        description = currentState.description,
                        priority = currentState.selectedPriority.toDomain() ?: TaskPriority.LOW,
                        status = TaskStatus.TO_DO,
                        createdDate = createDate,
                        categoryId = currentState.selectedCategoryId?.toLong()
                            ?: throw IllegalArgumentException("Selected category ID is null")
                    )
                )
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "An error occurred") }
            }
        }
    }
}