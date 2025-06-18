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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.DayItem
import com.example.tudeeapp.presentation.common.components.EmptyTasksSection
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskItemWithSwipe
import com.example.tudeeapp.presentation.common.components.TextTopBar
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeDatePickerDialog
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.task.components.DateHeader
import com.example.tudeeapp.presentation.utills.toStyle
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
            when{
                uiState.isLoading->{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = "Error: ${uiState.errorMessage}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else ->{
                    TaskScreenContent(uiState.data, viewModel, listState)
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
        TextTopBar(title = stringResource(R.string.tasks))

        if (data.showDatePicker) {
            TudeeDatePickerDialog(
                initialDate = data.calender.selectedDate,
                onDismiss = { viewModel.onDatePickerVisibilityChanged() },
                onSelectDate = { date ->
                    viewModel.onDateSelected(date)
                }
            )
        }

        DateHeader(
            data.calender.currentMonthYear,
            onClickNext = viewModel::goToPreviousMonth,
            onClickPrevious = viewModel::goToNextMonth,
            onClickPickDate = { viewModel.onDatePickerVisibilityChanged() }
        )

        LaunchedEffect(data.todayIndex) {
            if (data.todayIndex > 0) {
                listState.scrollToItem(data.todayIndex)
            }else if (data.todayIndex == 0){
                listState.scrollToItem(0)
            }
        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.calender.daysOfMonth) { day ->
                DayItem(
                    isSelected = data.calender.selectedDate.dayOfMonth == day.num,
                    dayNumber = day.num.toString(),
                    dayName = day.name,
                    onClick = {viewModel.onDateSelected(day.date)}
                )
            }
        }

        val tabs = data.status.map { status ->
            Tab(
                title = status.name,
                count = status.count,
                isSelected = status.isSelected
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.colors.surfaceColors.surface),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                EmptyTasksSection(
                    stringResource(R.string.no_tasks_here),
                    Modifier.fillMaxWidth().padding(start = 10.dp, end = 20.dp))
            }
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
                    priorityIcon = painterResource(task.priority.toStyle().iconRes),
                    priorityColor = task.priority.toStyle().backgroundColor,
                    isDated = false,
                    onClickItem = {}
                )
            }
        }
    }
}