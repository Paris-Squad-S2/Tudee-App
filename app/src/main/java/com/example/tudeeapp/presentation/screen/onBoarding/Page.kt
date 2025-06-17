package com.example.tudeeapp.presentation.screen.onBoarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tudeeapp.R


data class Page(
    val image: Int,
    val title : String,
    val description : String
)

@Composable
fun onboardingPages(): List<Page> {
    return listOf(
        Page(
            image = R.drawable.robot4,
            title = stringResource(id = R.string.onboarding1title),
            description = stringResource(id = R.string.onboarding1description)
        ),
        Page(
            image = R.drawable.robot6,
            title = stringResource(id = R.string.onboarding2title),
            description = stringResource(id = R.string.onboarding2description)
        ),
        Page(
            image = R.drawable.robot5,
            title = stringResource(id = R.string.onboarding3title),
            description = stringResource(id = R.string.onboarding3description)
        )
    )
}