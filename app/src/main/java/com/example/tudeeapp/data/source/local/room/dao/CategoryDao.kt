package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPredefinedCategories(categories: List<CategoryEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Query ("DELETE FROM category_table WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Long)

    @Query("SELECT * FROM category_table Where id = :categoryId")
    fun getCategoryById(categoryId: Long): Flow<CategoryEntity>

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getCategories(): Flow<List<CategoryEntity>>
}