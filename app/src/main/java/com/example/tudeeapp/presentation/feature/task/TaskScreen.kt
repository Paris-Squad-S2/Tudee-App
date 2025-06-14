package com.example.tudeeapp.presentation.feature.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tudeeapp.presentation.navigation.LocalNavController


@Composable
fun TaskScreen(taskId: Int, taskTitle: String) {
    val navController = LocalNavController.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Task Screen id is : $taskId and task title $taskTitle")

    }
}