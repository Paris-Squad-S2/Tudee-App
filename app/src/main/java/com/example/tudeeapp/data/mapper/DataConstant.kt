package com.example.tudeeapp.data.mapper

import com.example.tudeeapp.R
import com.example.tudeeapp.data.exception.MappingDrawableException
import com.example.tudeeapp.domain.models.Category

object DataConstant {

    val predefinedCategories = listOf(
        Category(
            title = "Education",
            imageUrl = "R.drawable.eduction",
            isPredefined = true,
        ),
        Category(
            title = "Shopping",
            imageUrl = "R.drawable.shooping",
            isPredefined = true,
        ),
        Category(
            title = "Medical",
            imageUrl = "R.drawable.medical",
            isPredefined = true,
        ),
        Category(
            title = "Gym",
            imageUrl = "R.drawable.gym",
            isPredefined = true,
        ),
        Category(
            title = "Entertainment",
            imageUrl = "R.drawable.entertainment",
            isPredefined = true,
        ),
        Category(
            title = "Cooking", imageUrl = "R.drawable.cooking",
            isPredefined = true
        ),
        Category(
            title = "Family & friend",
            imageUrl = "R.drawable.family_friend",
            isPredefined = true,
        ),
        Category(
            title = "Traveling",
            imageUrl = "R.drawable.traveling",
            isPredefined = true,
        ),
        Category(
            title = "Agriculture",
            imageUrl = "R.drawable.agriculture",
            isPredefined = true,
        ),
        Category(
            title = "Coding", imageUrl = "R.drawable.coding",
            isPredefined = true
        ),
        Category(
            title = "Adoration",
            imageUrl = "R.drawable.adoration",
            isPredefined = true,
        ),
        Category(
            title = "Fixing bugs",
            imageUrl = "R.drawable.fixing_bugs",
            isPredefined = true,
        ),
        Category(
            title = "Cleaning"
            ,imageUrl = "R.drawable.cleaning",
            isPredefined = true
        ),
        Category(
            title = "Work", imageUrl = "R.drawable.work",
            isPredefined = true
        ),
        Category(
            title = "Budgeting",
            imageUrl = "R.drawable.budgeting",
            isPredefined = true,
        ),
    )

    fun String.toResDrawables(): Int {
        return when (this) {
            "R.drawable.eduction" -> R.drawable.ic_education
            "R.drawable.shooping" -> R.drawable.ic_cart
            "R.drawable.medical" -> R.drawable.ic_medical
            "R.drawable.gym" -> R.drawable.ic_gym
            "R.drawable.entertainment" -> R.drawable.ic_entertainment
            "R.drawable.cooking" -> R.drawable.ic_cooking
            "R.drawable.family_friend" -> R.drawable.ic_family
            "R.drawable.traveling" -> R.drawable.ic_traveling
            "R.drawable.agriculture" -> R.drawable.ic_agriculture
            "R.drawable.coding" -> R.drawable.ic_coding
            "R.drawable.adoration" -> R.drawable.ic_adoration
            "R.drawable.fixing_bugs" -> R.drawable.ic_fixing_bugs
            "R.drawable.cleaning" -> R.drawable.ic_cleaning
            "R.drawable.work" -> R.drawable.ic_work
            "R.drawable.budgeting" -> R.drawable.ic_budgeting
            else -> throw MappingDrawableException(this)
        }
    }
}