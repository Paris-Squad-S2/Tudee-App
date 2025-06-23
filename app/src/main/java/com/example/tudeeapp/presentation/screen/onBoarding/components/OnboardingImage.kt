package com.example.tudeeapp.presentation.screen.onBoarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.tudeeapp.presentation.screen.onBoarding.Page

@Composable
fun OnboardingImage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = page.image),
                contentDescription = "Onboarding robot",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

