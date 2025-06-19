package com.example.tudeeapp.presentation.screen.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.DayItem
import com.example.tudeeapp.presentation.common.components.DeleteTaskConfirmationBox
import com.example.tudeeapp.presentation.common.components.EmptyTasksSection
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskItemWithSwipe
import com.example.tudeeapp.presentation.common.components.TextTopBar
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeDatePickerDialog
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.task.components.DateHeader
import com.example.tudeeapp.presentation.utills.toStyle
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(viewModel: TaskViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()

    TaskScreenContent(
        uiState = uiState,
        listState = rememberLazyListState(),
        addTask = { navController.navigate(Screens.TaskForm) },
        onCLickDatePicker = viewModel::onDatePickerVisibilityChanged,
        onClickPreviousMonth = viewModel::goToPreviousMonth,
        onClickNextMonth = viewModel::goToNextMonth,
        onTabSelected = viewModel::onTabSelected,
        onDateSelected = viewModel::onDateSelected,
        onClickDeleteIcon = viewModel::deleteTask,
        onclickTaskItem = { navController.navigate(Screens.TaskDetails(it)) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreenContent(
    uiState: TaskUiState,
    listState: LazyListState,
    addTask: () -> Unit,
    onCLickDatePicker: () -> Unit,
    onClickPreviousMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
    onTabSelected: (selectedStatus: TaskStatusUi) -> Unit,
    onDateSelected: (selectedDate: LocalDate) -> Unit,
    onClickDeleteIcon: (taskId: Long) -> Unit,
    onclickTaskItem: (id: Long) -> Unit,
) {
    TudeeScaffold(
        floatingActionButton = {
            TudeeButton(
                modifier = Modifier.size(64.dp),
                onClick = { addTask() },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_note_add),
                        contentDescription = null
                    )
                },
                variant = ButtonVariant.FloatingActionButton,
            )
        },
        topBar = {
            TextTopBar(title = stringResource(R.string.tasks))
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = "Error: ${uiState.errorMessage}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    TaskContent(
                        uiState.data,
                        listState,
                        onCLickDatePicker,
                        onClickPreviousMonth,
                        onClickNextMonth,
                        onTabSelected,
                        onDateSelected,
                        onClickDeleteIcon,
                        onclickTaskItem
                    )
                }
            }
        }
    }
}

@Composable
fun TaskContent(
    data: TasksUi,
    listState: LazyListState,
    onCLickDatePicker: () -> Unit,
    onClickPreviousMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
    onTabSelected: (selectedStatus: TaskStatusUi) -> Unit,
    onDateSelected: (selectedDate: LocalDate) -> Unit,
    onClickDeleteIcon: (taskId: Long) -> Unit,
    onclickTaskItem: (id: Long) -> Unit,
) {
    val statusList = TaskStatusUi.entries
    var isSheetOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceColors.surfaceHigh)
    ) {
        if (data.showDatePicker) {
            TudeeDatePickerDialog(
                initialDate = data.calender.selectedDate,
                onDismiss = { onCLickDatePicker() },
                onSelectDate = { date ->
                    onDateSelected(date)
                }
            )
        }

        DateHeader(
            data.calender.currentMonthYear,
            onClickNext = onClickNextMonth,
            onClickPrevious = onClickPreviousMonth,
            onClickPickDate = { onCLickDatePicker() }
        )

        LaunchedEffect(data.todayIndex) {
            val targetIndex = (data.todayIndex - 1)
                .coerceAtLeast(0)
            val visibleItems = listState.layoutInfo.visibleItemsInfo
                .map { it.index }

            if (targetIndex !in visibleItems) {
                listState.animateScrollToItem(targetIndex)
            }
        }

        LazyRow(
            state = listState,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.calender.daysOfMonth) { day ->
                DayItem(
                    isSelected = data.calender.selectedDate.dayOfMonth == day.num,
                    dayNumber = day.num.toString(),
                    dayName = day.name,
                    onClick = { onDateSelected(day.date) },
                    modifier = Modifier.width(56.dp)
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
                onTabSelected(statusList[index])
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke
                ),
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
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 20.dp)
                )
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
                    onClickItem = { onclickTaskItem(task.id) },
                    onDelete = { isSheetOpen = true }
                )
                TudeeBottomSheet(
                    isVisible = isSheetOpen,
                    title = "Delete task",
                    isScrollable = true,
                    skipPartiallyExpanded = true,
                    onDismiss = { isSheetOpen = false },
                    content = {
                        DeleteTaskConfirmationBox(
                            onConfirm = {
                                onClickDeleteIcon(task.id)
                                isSheetOpen = false
                            },
                            onDismiss = { isSheetOpen = false })
                    },
                )
            }
        }
    }

}