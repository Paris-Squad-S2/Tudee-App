package com.example.tudeeapp.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens

@Composable
fun HomeScreen(userName: String) {
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome To Home $userName", modifier = Modifier.padding(16.dp))

        Box(
            modifier = Modifier
                .background(Color.Green)
                .clickable {
                    navController.navigate(
                        Screens.Task(
                            taskId = 12,
                            taskTitle = "taskTitle"
                        )
                    )
                }
        ) {
            Text("Go to task screen", modifier = Modifier.padding(16.dp))
        }
    }
}