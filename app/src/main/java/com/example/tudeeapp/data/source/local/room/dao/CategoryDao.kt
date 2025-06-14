package com.example.tudeeapp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(categoryEntity: CategoryEntity)
    @Query("SELECT * FROM CategoryEntity Where id = :categoryId")
    fun getCategory(categoryId: Long): CategoryEntity
}