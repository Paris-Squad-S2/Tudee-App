package com.example.tudeeapp.presentation.screen.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import com.example.tudeeapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tudeeapp.presentation.design_system.text_style.cherryBomb
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(Screens.OnBoarding)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.colors.status.overlay)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_vectors),
            contentDescription = "Splash Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedFilledText(stringResource(id = R.string.app_name))
        }
    }
}

@Composable
fun OutlinedFilledText(
    text: String,
    fillColor: Color = Theme.colors.surfaceColors.onPrimaryColors.onPrimary,
    strokeColor: Color = Theme.colors.primary ,
    strokeWidth: Float = 6f
) {
    Box {
        Text(
            text = text,
            fontSize = 48.sp,
            fontFamily = cherryBomb,
            color = strokeColor,
            style = TextStyle(
                drawStyle = Stroke(
                    width = strokeWidth,
                    miter = 10f,
                    join = StrokeJoin.Round
                )
            )
        )
        Text(
            text = text,
            fontSize = 48.sp,
            fontFamily = cherryBomb,
            color = fillColor
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenPreview(){
    SplashScreen()
}