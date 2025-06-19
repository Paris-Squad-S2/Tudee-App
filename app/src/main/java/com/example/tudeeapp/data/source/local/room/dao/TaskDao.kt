package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(taskEntity: TaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editTask(taskEntity: TaskEntity)

    @Query("DELETE FROM TASK_TABLE WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("SELECT * FROM TASK_TABLE Where id = :taskId")
    fun getTaskById(taskId: Long): Flow<TaskEntity>

    @Query("SELECT * FROM TASK_TABLE")
    fun getAllTasks(): Flow<List<TaskEntity>>
}