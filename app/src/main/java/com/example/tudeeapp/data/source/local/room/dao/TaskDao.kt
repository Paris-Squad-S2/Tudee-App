package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun insertTask(taskEntity: TaskEntity)
}