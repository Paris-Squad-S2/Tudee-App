package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(taskEntity: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertList(taskEntity: List<TaskEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(taskEntity: TaskEntity)

    @Delete()
    suspend fun delete(taskEntity: TaskEntity)

    @Query("SELECT * FROM TASK_TABLE Where id = :taskId")
    fun findById(taskId: Long): Flow<TaskEntity>

    @Query("SELECT * FROM TASK_TABLE")
    fun getAll(): Flow<List<TaskEntity>>
}