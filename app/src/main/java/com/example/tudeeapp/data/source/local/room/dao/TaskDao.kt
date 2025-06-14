package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Insert

interface TaskDao {
    @Insert
    fun insertTask(taskDao: TaskDao)
}