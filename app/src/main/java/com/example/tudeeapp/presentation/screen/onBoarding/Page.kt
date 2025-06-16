package com.example.tudeeapp.presentation.screen.onBoarding

import com.example.tudeeapp.R


data class Page(
    val image: Int,
    val title : String,
    val description : String
)

val pages = listOf(
    Page(
        image = R.drawable.robot4,
        title = "Overwhelmed with tasks?",
        description = "Let’s bring some order to the chaos. Tudee is here to help you sort, plan, and breathe easier."
    ),
    Page(
        image = R.drawable.robot6,
        title = "Uh-oh! Procrastinating again",
        description = "Tudee not mad… just a little disappointed, Let’s get back on track together."
    ),
    Page(
        image = R.drawable.robot5,
        title = "Let’s complete task and celebrate\n" +
                "together.",
        description = "Progress is progress. Tudee will celebrate you on for every win, big or small."
    ),
)