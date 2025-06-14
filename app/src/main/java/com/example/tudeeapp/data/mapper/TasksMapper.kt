package com.example.tudeeapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter

fun String.toTaskPriority(): TaskPriority = when(this.uppercase()) {
    "LOW" -> TaskPriority.LOW
    "MEDIUM" -> TaskPriority.MEDIUM
    "HIGH" -> TaskPriority.HIGH
    else -> TaskPriority.LOW
}

fun String.toTaskStatus(): TaskStatus = when (this.uppercase()) {
    "TO DO" -> TaskStatus.TO_DO
    "IN PROGRESS" -> TaskStatus.IN_PROGRESS
    "DONE" -> TaskStatus.DONE
    else -> TaskStatus.TO_DO
}

@RequiresApi(Build.VERSION_CODES.O)
private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
fun String.toLocalDate(): LocalDate = LocalDate.parse(this, formatter)

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toTaskPriority(),
        status = this.status.toTaskStatus()
    )
}