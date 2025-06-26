package com.example.tudeeapp.presentation.utills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun ShowLoading() {

    val animationComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_square_loader))

    val progress by animateLottieCompositionAsState(
        composition = animationComposition,
        iterations = LottieConstants.IterateForever,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surfaceColors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = animationComposition,
            progress = { progress },
        )

    }
}
