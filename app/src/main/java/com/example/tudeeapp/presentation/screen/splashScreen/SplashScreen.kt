package com.example.tudeeapp.presentation.screen.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.component.LocalNavController
import com.example.tudeeapp.presentation.screen.onBoardScreen.navigateToOnBoardScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigateToOnBoardScreen()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("splash Screen", modifier = Modifier.padding(16.dp))

    }
}