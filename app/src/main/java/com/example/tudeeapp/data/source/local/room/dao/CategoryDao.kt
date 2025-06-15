package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPredefinedCategories(categories: List<CategoryEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(categoryEntity: CategoryEntity)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category_table Where id = :categoryId")
    fun findById(categoryId: Long): Flow<CategoryEntity>

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getAll(): Flow<List<CategoryEntity>>
}