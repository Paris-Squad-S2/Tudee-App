package com.example.tudeeapp.data

import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.Category

object DataConstant {

   val predefinedCategories = listOf(
        Category(title = "Education", imageUrl = "R.drawable.eduction", isPredefined = true,id = 0),
        Category(title = "Shopping", imageUrl = "R.drawable.shooping", isPredefined = true,id = 0),
        Category(title = "Medical", imageUrl = "R.drawable.medical", isPredefined = true,id = 0),
        Category(title = "Gym", imageUrl = "R.drawable.gym", isPredefined = true,id = 0),
        Category(title = "Entertainment", imageUrl = "R.drawable.entertainment", isPredefined = true,id = 0),
        Category(title = "Cooking", imageUrl = "R.drawable.cooking", isPredefined = true,id = 0),
        Category(title = "Family & friend", imageUrl = "R.drawable.family_friend", isPredefined = true,id = 0),
        Category(title = "Traveling", imageUrl = "R.drawable.traveling", isPredefined = true,id = 0),
        Category(title = "Agriculture", imageUrl = "R.drawable.agriculture", isPredefined = true,id = 0),
        Category(title = "Coding", imageUrl = "R.drawable.coding", isPredefined = true,id = 0),
        Category(title = "Adoration", imageUrl = "R.drawable.adoration", isPredefined = true,id = 0),
        Category(title = "Fixing bugs", imageUrl = "R.drawable.fixing_bugs", isPredefined = true,id = 0),
        Category(title = "Cleaning", imageUrl = "R.drawable.cleaning", isPredefined = true,id = 0),
        Category(title = "Work", imageUrl = "R.drawable.work", isPredefined = true,id = 0),
        Category(title = "Budgeting", imageUrl = "R.drawable.budgeting", isPredefined = true,id = 0),
    )

    // replace with the actual drawables images
      fun String.toResDrawables():Int{
        return when(this){
               "R.drawable.eduction" -> R.drawable.ic_launcher_background
               "R.drawable.shooping" -> R.drawable.ic_launcher_background
               "R.drawable.medical" -> R.drawable.ic_launcher_background
               "R.drawable.gym" -> R.drawable.ic_launcher_background
               "R.drawable.entertainment" -> R.drawable.ic_launcher_background
               "R.drawable.cooking" -> R.drawable.ic_launcher_background
               "R.drawable.family_friend" -> R.drawable.ic_launcher_background
               "R.drawable.traveling" -> R.drawable.ic_launcher_background
               "R.drawable.agriculture" -> R.drawable.ic_launcher_background
               "R.drawable.coding" -> R.drawable.ic_launcher_background
               "R.drawable.adoration" -> R.drawable.ic_launcher_background
               "R.drawable.fixing_bugs" -> R.drawable.ic_launcher_background
               "R.drawable.cleaning" -> R.drawable.ic_launcher_background
               "R.drawable.work" -> R.drawable.ic_launcher_background
               "R.drawable.budgeting" -> R.drawable.ic_launcher_background
               else -> R.drawable.ic_launcher_background
        }
     }

}