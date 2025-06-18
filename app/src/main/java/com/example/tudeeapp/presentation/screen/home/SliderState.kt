package com.example.tudeeapp.presentation.screen.home

import androidx.annotation.DrawableRes
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus

sealed class SliderViewState(
    val title: String,
    @DrawableRes val icon: Int,
    @DrawableRes val image: Int,
    val description: String
) {
    object Nothing : SliderViewState(
        title = "Nothing on your list…",
        icon = R.drawable.ic_poor,
        image = R.drawable.img_ropot1,
        description = "Fill your day with something awesome."
    )

    data class Tadaa(val count: Int, val total: Int) : SliderViewState(
        title = "Tadaa!",
        icon = R.drawable.ic_good,
        image = R.drawable.img_ropot2,
        description = "You’re doing amazing!!!\nTudee is proud of you."
    )

    data class ZeroProgress(val count: Int, val total: Int) : SliderViewState(
        title = "Zero progress?!",
        icon = R.drawable.ic_bad,
        image = R.drawable.img_ropot3,
        description = "You just scrolling, not working. Tudee is watching. Back to work!!!"
    )

    data class StayWorking(val done: Int, val total: Int) : SliderViewState(
        title = "Stay working!",
        icon = R.drawable.ic_okay,
        image = R.drawable.img_ropot1,
        description = "You've completed $done out of $total tasks. Keep going!"
    )
}

fun mapTaskCountToSliderState(taskCount: Map<TaskStatus, Int>): SliderViewState {
    val total = taskCount.values.sum()
    val done = taskCount[TaskStatus.DONE] ?: 0
    val toDo = taskCount[TaskStatus.TO_DO] ?: 0

    return when {
        total == 0 -> SliderViewState.Nothing
        done > 0 && done == total -> SliderViewState.Tadaa(done, total)
        done == 0 -> SliderViewState.ZeroProgress(toDo, total)
        else -> SliderViewState.StayWorking(done, total)
    }
}