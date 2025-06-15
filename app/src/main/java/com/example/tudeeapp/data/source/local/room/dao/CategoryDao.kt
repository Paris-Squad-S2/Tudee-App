package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPredefinedCategories(categories: List<CategoryEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categoryEntity: CategoryEntity)

    @Delete
    fun delete(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category_table Where id = :categoryId")
    fun findById(categoryId: Long): CategoryEntity

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getAll(): List<CategoryEntity>
}