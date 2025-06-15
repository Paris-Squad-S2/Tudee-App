package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(taskEntity: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertList(taskEntity: List<TaskEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(taskEntity: TaskEntity)

    @Delete()
    fun delete(taskEntity: TaskEntity)

    @Query("SELECT * FROM TASK_TABLE Where id = :taskId")
    fun findById(taskId: Long): TaskEntity

    @Query("SELECT * FROM TASK_TABLE")
    fun getAll(): List<TaskEntity>
}