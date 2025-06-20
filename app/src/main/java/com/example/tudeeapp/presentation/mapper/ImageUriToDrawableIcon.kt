package com.example.tudeeapp.presentation.mapper

import com.example.tudeeapp.R
import com.example.tudeeapp.data.exception.MappingDrawableException

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