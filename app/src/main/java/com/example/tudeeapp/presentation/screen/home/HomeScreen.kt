package com.example.tudeeapp.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.Header
import com.example.tudeeapp.presentation.common.components.Slider
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.home.composable.EmptyTasksSection
import com.example.tudeeapp.presentation.screen.home.composable.HomeTaskSection
import com.example.tudeeapp.presentation.screen.home.composable.OverviewCard
import com.example.tudeeapp.presentation.screen.home.utils.getLocalizedToday
import kotlinx.datetime.LocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    var toggled by remember { mutableStateOf(false) } //TODO
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()

    TudeeScaffold(
        topBar = {
            Header(
                modifier = Modifier
                    .background(Theme.colors.primary)
                    .statusBarsPadding(),
                isDarkMode = toggled, //TODO
                onToggleTheme = { toggled = it }, //TODO
            )
        },
        floatingActionButton = {
            TudeeButton(
                modifier = Modifier.size(64.dp),
                onClick = { navController.navigate(Screens.TaskForm) },
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
                        HomeLoadingScreen()
                    }

                    state.error != null -> {
                        HomeErrorScreen(state.error.toString())
                    }

                    state.isSuccess -> {
                        HomeContent(
                            state = state,
                            navController = navController
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun HomeContent(
    state: HomeViewModeUiState,
    navController: NavController
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
                    EmptyTasksSection(
                        Modifier
                            .padding(top = 58.dp, bottom = 27.dp)
                            .fillMaxWidth()
                            .height(160.dp)
                    )
                }
            }
        }
        if (state.inProgressTasks.isNotEmpty()) {
            item {
                HomeTaskSection(
                    tasks = state.inProgressTasks,
                    tasksType = TaskStatus.IN_PROGRESS,
                    onTasksCountClick = {
                        navController.navigate(
                            Screens.Task(
                                TaskStatus.IN_PROGRESS.name
                            )
                        )
                    },
                    onTaskClick = { taskId ->
                        navController.navigate(
                            Screens.TaskDetails(taskId)
                        )
                    }
                )
            }
        }
        if (state.toDoTasks.isNotEmpty()) {
            item {
                HomeTaskSection(
                    tasks = state.toDoTasks,
                    tasksType = TaskStatus.TO_DO,
                    onTasksCountClick = {
                        navController.navigate(
                            Screens.Task(
                                TaskStatus.TO_DO.name
                            )
                        )
                    },
                    onTaskClick = { taskId ->
                        navController.navigate(
                            Screens.TaskDetails(taskId)
                        )
                    }
                )
            }
        }
        if (state.doneTasks.isNotEmpty()) {
            item {
                HomeTaskSection(
                    tasks = state.doneTasks,
                    tasksType = TaskStatus.DONE,
                    onTasksCountClick = {
                        navController.navigate(
                            Screens.Task(
                                TaskStatus.DONE.name
                            )
                        )
                    },
                    onTaskClick = { taskId ->
                        navController.navigate(
                            Screens.TaskDetails(taskId)
                        )
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HomeErrorScreen(error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surfaceColors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_ropo_cry),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(error, fontSize = 22.sp, color = Theme.colors.text.title)
    }
}

@Composable
fun HomeLoadingScreen() {
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
        Slider(
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
            text = "Overview", modifier = Modifier.padding(bottom = 8.dp),
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
            label = "Done"
        )
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.yellowAccent,
            painter = painterResource(id = R.drawable.ic_in_progress),
            stat = inProgressTasksCount,
            label = "In Progress",
        )
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.purpleAccent,
            painter = painterResource(id = R.drawable.ic_to_do),
            stat = toDoTasksCount,
            label = "To-Do",
        )
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}
