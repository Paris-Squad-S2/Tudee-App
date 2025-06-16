package com.example.tudeeapp.presentation.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.tudeeapp.presentation.navigation.LocalNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.DayItem
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskCard
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme


@Composable
fun TaskScreen(taskId: Int, taskTitle: String) {
    val navController = LocalNavController.current

//    Column(modifier = Modifier
//        .fillMaxWidth()
//        .background(Theme.colors.surfaceColors.surfaceHigh)
//    ) {
//        Text(
//            text = "Task",
//            style = Theme.textStyle.title.large,
//            color = Theme.colors.text.title,
//            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp)
//        )
//        DateHeader("Jun, 2025")
//        LazyRow (modifier = Modifier
//            .padding(vertical = 8.dp, horizontal = 16.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ){
//            item { DayItem(false, "14", "Sun") }
//            item { DayItem(true, "15", "Mon") }
//            item { DayItem(false, "16", "Tue") }
//            item { DayItem(false, "17", "Wed") }
//            item { DayItem(false, "18", "Thu") }
//            item { DayItem(false, "19", "Tue") }
//        }
//
//        val tabs = listOf(
//            Tab(title = "In progress", count = 14, isSelected = true),
//            Tab(title = "To Do", count = 5),
//            Tab(title = "Done")
//        )
//
//        HorizontalTabs(
//            modifier = Modifier.height(48.dp),
//            tabs = tabs,
//            selectedTabIndex = 0,
//            onTabSelected = { },
//        )
//
//        LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier
//                .background(Theme.colors.surfaceColors.surface)
//                .padding(vertical = 12.dp, horizontal = 16.dp)
//        ){
//            item {
//                TaskCard(
//                    icon = painterResource(R.drawable.ic_cooking),
//                    iconColor = Color.Unspecified,
//                    title = "Organize Study Desk",
//                    date = "2023-10-15",
//                    subtitle = "Review cell structure and functions for tomorrow...",
//                    priorityLabel = "Medium",
//                    priorityIcon = painterResource(R.drawable.ic_alert),
//                    priorityColor = Theme.colors.status.yellowAccent,
//                    isDated = false
//                )}
//            item {
//                TaskCard(
//                    icon = painterResource(R.drawable.ic_agriculture),
//                    iconColor = Color.Unspecified,
//                    title = "Organize Study Desk",
//                    date = "2023-10-15",
//                    subtitle = "Review cell structure and functions for tomorrow...",
//                    priorityLabel = "Low",
//                    priorityIcon = painterResource(R.drawable.ic_trade_down),
//                    priorityColor = Theme.colors.status.greenAccent,
//                    isDated = false
//                )
//            }
//
//            item {
//                TaskCard(
//                    icon = painterResource(R.drawable.ic_gym),
//                    iconColor = Color.Unspecified,
//                    title = "Organize Study Desk",
//                    date = "2023-10-15",
//                    subtitle = "Review cell structure and functions for tomorrow...",
//                    priorityLabel = "Medium",
//                    priorityIcon = painterResource(R.drawable.ic_flag),
//                    priorityColor = Theme.colors.status.pinkAccent,
//                    isDated = true
//                )
//            }
//
//            item {
//                TaskCard(
//                    icon = painterResource(R.drawable.ic_education),
//                    iconColor = Color.Unspecified,
//                    title = "Organize Study Desk",
//                    date = "2023-10-15",
//                    subtitle = "Review cell structure and functions for tomorrow...",
//                    priorityLabel = "Medium",
//                    priorityIcon = painterResource(R.drawable.ic_self_care),
//                    priorityColor = Theme.colors.status.yellowAccent,
//                    isDated = true
//                )
//            }
//        }
//    }
}

@PreviewMultiDevices
@Composable
fun TaskScreenPreview(){
    BasePreview {
        Column {
//            TaskScreen()
        }
    }
}