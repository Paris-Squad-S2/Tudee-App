package com.example.tudeeapp.presentation.screen.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.utills.TaskPriorityUi


data class HomeUiState(
    val inProgressTasks: List<TaskUiState> = emptyList(),
    val toDoTasks: List<TaskUiState> = emptyList(),
    val doneTasks: List<TaskUiState> = emptyList(),
    val isTasksEmpty: Boolean = true,
    val sliderState: SliderUiState = SliderUiState.NothingState,
    val isDarkMode: Int = 2,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)


data class TaskUiState(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val priorityResIcon: Int,
    val status: TaskStatus,
    val categoryIcon: String = "R.drawable.ic_loading" ,
    val isCategoryPredefined: Boolean
)


sealed class SliderUiState(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val descriptionRes: Int,
    val formatArgs: Array<Any> = emptyArray()
) {
    object NothingState : SliderUiState(
        titleRes = R.string.slider_title_nothing,
        iconRes = R.drawable.ic_poor,
        imageRes = R.drawable.img_ropot1,
        descriptionRes = R.string.slider_description_nothing
    )

    data class GoodState(val count: Int, val total: Int) : SliderUiState(
        titleRes = R.string.slider_title_tadaa,
        iconRes = R.drawable.ic_good,
        imageRes = R.drawable.img_ropot2,
        descriptionRes = R.string.slider_description_tadaa
    )

    data class ZeroProgressState(val count: Int, val total: Int) : SliderUiState(
        titleRes = R.string.slider_title_zero_progress,
        iconRes = R.drawable.ic_bad,
        imageRes = R.drawable.img_ropot3,
        descriptionRes = R.string.slider_description_zero_progress
    )

    data class StayWorkingState(val done: Int, val total: Int) : SliderUiState(
        titleRes = R.string.slider_title_stay_working,
        iconRes = R.drawable.ic_okay,
        imageRes = R.drawable.img_ropot1,
        descriptionRes = R.string.slider_description_stay_working,
        formatArgs = arrayOf(done, total)
    )
}


