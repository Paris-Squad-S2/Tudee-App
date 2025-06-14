package com.example.tudeeapp.presentation.screen.firstScreen

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
import com.example.tudeeapp.presentation.component.LocalNavController

@Composable
fun FirstScreen() {

    val navController = LocalNavController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("First Screen", modifier = Modifier.padding(16.dp))

        Box(
            modifier = Modifier
                .background(Color.Green)
                .clickable {
                    navController.navigateToSecondScreen("gdfhfdh")
                }
        ) {
            Text("Go to Second", modifier = Modifier.padding(16.dp))
        }
    }
}
