package com.example.tudeeapp.presentation.screen.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.DayItem
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskItemWithSwipe
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeDatePickerDialog
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.task.components.DateHeader
import com.example.tudeeapp.presentation.screen.task.components.EmptyTaskState
import com.example.tudeeapp.presentation.screen.task.mapper.getStyle
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(viewModel: TaskViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    TudeeScaffold(
        floatingActionButton = {
            TudeeButton(
                modifier = Modifier.size(64.dp),
                onClick = { Screens.TaskForm::class.qualifiedName?.let { navController.navigate(it) } },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_note_add),
                        contentDescription = null)
                },
                variant = ButtonVariant.FloatingActionButton,
            )
        },
        content = {
            when(uiState){
                is TaskUiState.isLoading->{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is TaskUiState.error -> {
                    val errorMsg = (uiState as TaskUiState.error).message
                    Text(
                        text = "Error: $errorMsg",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is TaskUiState.success->{
                    val data = (uiState as TaskUiState.success).data
                    TaskScreenContent(data, viewModel, listState)
                }
            }

        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreenContent(data: TasksUi, viewModel: TaskViewModel, listState: LazyListState) {
    val statusList = TaskStatusUi.entries
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceColors.surfaceHigh)
    ) {
        Text(
            text = "Task",
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .statusBarsPadding()
        )

        if (data.showDatePicker) {
            TudeeDatePickerDialog(
                initialDate = data.selectedDate,
                onDismiss = { viewModel.onDatePickerVisibilityChanged(false)  },
                onSelectDate = { date ->
                    viewModel.onDateSelected(date)
                }
            )
        }

        DateHeader(
            data.currentMonthYear,
            onClickNext = viewModel::goToPreviousMonth,
            onClickPrevious = viewModel::goToNextMonth,
            onClickPickDate = { viewModel.onDatePickerVisibilityChanged(true) }
        )

        LaunchedEffect(data.selectedDate, data.todayIndex) {
            if (data.todayIndex > 0) {
                listState.scrollToItem(data.todayIndex-1)
            }else if (data.todayIndex == 0){
                listState.scrollToItem(data.todayIndex)
            }
        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.daysOfMonth) { date ->
                DayItem(
                    isSelected = data.selectedDate == date,
                    dayNumber = date.dayOfMonth.toString(),
                    dayName = viewModel.formatDayName(date),
                    onClick = {viewModel.onDateSelected(date)}
                )
            }
        }

        val tabs = statusList.map { status ->
            Tab(
                title = status.name.replace("_", " "),
                count = data.tasks.count { it.status == status },
                isSelected = data.selectedStatus == status
            )
        }
        HorizontalTabs(
            modifier = Modifier.height(48.dp),
            tabs = tabs,
            selectedTabIndex = statusList.indexOf(data.selectedStatus),
            onTabSelected = { index ->
                viewModel.onTabSelected(statusList[index])
            },
        )

        if (data.tasks.isEmpty()) {
            EmptyTaskState()
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Theme.colors.surfaceColors.surfaceLow)
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(data.tasks) { task ->
                TaskItemWithSwipe(
                    icon = painterResource(R.drawable.ic_cooking),
                    iconColor = Color.Unspecified,
                    title = task.title,
                    date = task.createdDate.toString(),
                    subtitle = task.description,
                    priorityLabel = task.priority.name,
                    priorityIcon = painterResource(task.priority.getStyle().iconRes),
                    priorityColor = task.priority.getStyle().backgroundColor,
                    isDated = false,
                )
            }
        }
    }
}