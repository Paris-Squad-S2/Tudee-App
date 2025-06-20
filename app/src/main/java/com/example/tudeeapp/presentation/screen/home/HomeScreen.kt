package com.example.tudeeapp.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.Header
import com.example.tudeeapp.presentation.common.components.TudeeHomeMessage
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.home.composable.HomeEmptyTasksSection
import com.example.tudeeapp.presentation.screen.home.composable.HomeTaskSection
import com.example.tudeeapp.presentation.screen.home.composable.OverviewCard
import com.example.tudeeapp.presentation.screen.home.state.HomeUiState
import com.example.tudeeapp.presentation.screen.home.utils.getLocalizedToday
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        onToggleTheme = homeViewModel::onToggledAction,
        onFloatingActionButtonClick = { navController.navigate(Screens.TaskManagement()) },
        onTasksCountClick = { tasksTitle -> navController.navigate(Screens.Task(tasksTitle)) },
        onTaskClick = { taskId -> navController.navigate(Screens.TaskDetails(taskId)) },
    )

}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onToggleTheme: (Boolean) -> Unit,
    onFloatingActionButtonClick: () -> Unit,
    onTasksCountClick: (String) -> Unit,
    onTaskClick: (Long) -> Unit,
) {
    TudeeScaffold(
        topBar = {
            Header(
                modifier = Modifier
                    .background(Theme.colors.primary)
                    .statusBarsPadding(),
                isDarkMode = state.isDarkMode,
                onToggleTheme = onToggleTheme,
            )
        },
        floatingActionButton = {
            TudeeButton(
                modifier = Modifier.size(64.dp),
                onClick = onFloatingActionButtonClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_note_add),
                        contentDescription = null
                    )
                },
                variant = ButtonVariant.FloatingActionButton
            )
        },
        contentBackground = Theme.colors.surfaceColors.surface,
        content = {
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Theme.colors.primary)
                        .align(Alignment.TopCenter)
                )

                when {
                    state.isLoading -> {
                        ShowLoading()
                    }

                    state.error != null -> {
                        ShowError(state.error.toString())
                    }

                    state.isSuccess -> {
                        HomeContent(
                            state = state,
                            onTasksCountClick = onTasksCountClick,
                            onTaskClick = onTaskClick
                        )
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HomeContent(
    state: HomeUiState,
    onTasksCountClick: (String) -> Unit,
    onTaskClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            OverViewSection(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                doneTasksCount = state.doneTasks.size,
                inProgressTasksCount = state.inProgressTasks.size,
                toDoTasksCount = state.toDoTasks.size
            )
        }

        if (state.isTasksEmpty) {
            item {
                AnimatedVisibility(
                    modifier = Modifier
                        .background(Theme.colors.surfaceColors.surface)
                        .padding(horizontal = 16.dp),
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    HomeEmptyTasksSection(
                        title = stringResource(R.string.no_tasks_for_today),
                        Modifier
                            .padding(top = 70.dp)
                    )
                }
            }

        }
        if (state.inProgressTasks.isNotEmpty()) {
            item {
                HomeTaskSection(
                    tasks = state.inProgressTasks,
                    tasksType = TaskStatus.IN_PROGRESS,
                    onTasksCountClick = onTasksCountClick,
                    onTaskClick = onTaskClick
                )
            }

        }
        if (state.toDoTasks.isNotEmpty()) {
            item {
                HomeTaskSection(
                    tasks = state.toDoTasks,
                    tasksType = TaskStatus.TO_DO,
                    onTasksCountClick = onTasksCountClick,
                    onTaskClick = onTaskClick
                )
            }
        }
        if (state.doneTasks.isNotEmpty()) {
            item {
                HomeTaskSection(
                    tasks = state.doneTasks,
                    tasksType = TaskStatus.DONE,
                    onTasksCountClick = onTasksCountClick,
                    onTaskClick = onTaskClick
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShowError(error: String) {

    Box(
        modifier = Modifier
            .background(Theme.colors.surfaceColors.surface)
            .fillMaxSize()
            .padding(end = 40.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(144.dp)
                .align(Alignment.CenterEnd)
        ) {

            Image(
                painter = painterResource(R.drawable.task_card_ropo_background),
                contentDescription = "ropo",
                modifier = Modifier
                    .size(136.dp)
                    .align(Alignment.TopEnd)
            )


            Image(
                painter = painterResource(R.drawable.task_card_ropo_container),
                contentDescription = "ropo",
                modifier = Modifier
                    .size(144.dp)
                    .offset(x = (-4).dp, y = (-9).dp)
            )

            Image(
                painter = painterResource(R.drawable.task_card_dots),
                contentDescription = "ropo2",
                modifier = Modifier
                    .size(54.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-2).dp, y = (10).dp)

            )

            Image(
                painter = painterResource(R.drawable.img_ropo_cry),
                contentDescription = "ropo4",
                modifier = Modifier
                    .width(107.dp)
                    .height(100.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 0.dp, y = (-11).dp)

            )
        }
        Column(
            modifier = Modifier
                .padding(top = 222.dp, end = 90.dp)
                .width(203.dp)
                .height(74.dp)
                .background(
                    color = Theme.colors.surfaceColors.surfaceHigh,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 2.dp
                    )
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error,
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.hint,
                maxLines = 2
            )
        }

    }
}

@Composable
fun ShowLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surfaceColors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .size(40.dp)
        )
        Text("Loading..", fontSize = 12.sp)
    }
}


@Composable
private fun OverViewSection(
    modifier: Modifier = Modifier,
    doneTasksCount: Int,
    inProgressTasksCount: Int,
    toDoTasksCount: Int
) {
    val todayText = stringResource(id = R.string.date_format_today)

    val formattedDate = remember {
        getLocalizedToday()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Theme.colors.surfaceColors.surfaceHigh,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(top = 8.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_date),
                contentDescription = todayText,
                tint = Theme.colors.text.body
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "$todayText, $formattedDate",
                style = Theme.textStyle.label.medium,
                color = Theme.colors.text.body
            )
        }
        TudeeHomeMessage(
            modifier = Modifier.padding(
                bottom = 8.dp,
                start = 6.dp,
                end = 6.dp
            ),
            taskCount = mapOf<TaskStatus, Int>(
                TaskStatus.TO_DO to toDoTasksCount,
                TaskStatus.IN_PROGRESS to inProgressTasksCount,
                TaskStatus.DONE to doneTasksCount
            )
        )
        OverViewContainer(
            toDoTasksCount = toDoTasksCount,
            inProgressTasksCount = inProgressTasksCount,
            doneTasksCount = doneTasksCount
        )
    }
}

@Composable
fun OverViewContainer(
    modifier: Modifier = Modifier,
    toDoTasksCount: Int,
    inProgressTasksCount: Int,
    doneTasksCount: Int
) {
    Column(
        modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.overview), modifier = Modifier.padding(bottom = 8.dp),
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title
        )
        OverViewCardsRow(
            toDoTasksCount = toDoTasksCount,
            inProgressTasksCount = inProgressTasksCount,
            doneTasksCount = doneTasksCount
        )
    }

}

@Composable
private fun OverViewCardsRow(
    toDoTasksCount: Int,
    inProgressTasksCount: Int,
    doneTasksCount: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.greenAccent,
            painter = painterResource(id = R.drawable.ic_done),
            stat = doneTasksCount,
            label = stringResource(R.string.done)
        )
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.yellowAccent,
            painter = painterResource(id = R.drawable.ic_in_progress),
            stat = inProgressTasksCount,
            label = stringResource(R.string.in_progress_text),
        )
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.purpleAccent,
            painter = painterResource(id = R.drawable.ic_to_do),
            stat = toDoTasksCount,
            label = stringResource(R.string.to_do),
        )
    }
}


