package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun addOrUpdateTask(taskEntity: TaskEntity)

    @Query("DELETE FROM TASK_TABLE WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("DELETE FROM TASK_TABLE WHERE categoryId = :categoryId")
    suspend fun deleteTasksByCategoryId(categoryId: Long)

    @Query("SELECT * FROM TASK_TABLE Where id = :taskId")
    fun getTaskById(taskId: Long): Flow<TaskEntity>

    @Query("SELECT * FROM TASK_TABLE ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>
}