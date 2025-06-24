package com.example.tudeeapp.presentation.screen.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus

sealed class SliderViewState(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val descriptionRes: Int,
    val formatArgs: Array<Any> = emptyArray()
) {
    object NothingState : SliderViewState(
        titleRes = R.string.slider_title_nothing,
        iconRes = R.drawable.ic_poor,
        imageRes = R.drawable.img_ropot1,
        descriptionRes = R.string.slider_description_nothing
    )

    data class GoodState(val count: Int, val total: Int) : SliderViewState(
        titleRes = R.string.slider_title_tadaa,
        iconRes = R.drawable.ic_good,
        imageRes = R.drawable.img_ropot2,
        descriptionRes = R.string.slider_description_tadaa
    )

    data class ZeroProgressState(val count: Int, val total: Int) : SliderViewState(
        titleRes = R.string.slider_title_zero_progress,
        iconRes = R.drawable.ic_bad,
        imageRes = R.drawable.img_ropot3,
        descriptionRes = R.string.slider_description_zero_progress
    )

    data class StayWorkingState(val done: Int, val total: Int) : SliderViewState(
        titleRes = R.string.slider_title_stay_working,
        iconRes = R.drawable.ic_okay,
        imageRes = R.drawable.img_ropot1,
        descriptionRes = R.string.slider_description_stay_working,
        formatArgs = arrayOf(done, total)
    )
}

fun mapTaskCountToSliderState(taskCount: Map<TaskStatus, Int>): SliderViewState {
    val total = taskCount.values.sum()
    val done = taskCount[TaskStatus.DONE] ?: 0
    val toDo = taskCount[TaskStatus.TO_DO] ?: 0

    return when {
        total == 0 -> SliderViewState.NothingState
        done > 0 && done == total -> SliderViewState.GoodState(done, total)
        done == 0 -> SliderViewState.ZeroProgressState(toDo, total)
        else -> SliderViewState.StayWorkingState(done, total)
    }
}