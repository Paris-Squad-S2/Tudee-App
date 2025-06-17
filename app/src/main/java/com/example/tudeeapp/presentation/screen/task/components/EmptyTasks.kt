package com.example.tudeeapp.presentation.screen.task.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun EmptyTaskState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "No tasks here!",
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.text.body
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap the + button to add your first one.",
                    style = Theme.textStyle.body.small,
                    color = Theme.colors.text.hint
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_tudee),
                contentDescription = "Robot",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview
@Composable
fun EmptyTaskStatePreview(){
    EmptyTaskState()
}