package com.example.tudeeapp.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.LocalThemeState
import com.example.tudeeapp.presentation.common.components.AppHeader
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.EmptyTasksSection
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeHomeMessage
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.home.components.HomeTaskSection
import com.example.tudeeapp.presentation.screen.home.components.OverviewCard
import com.example.tudeeapp.presentation.screen.home.utils.getLocalizedToday
import com.example.tudeeapp.presentation.utills.ShowError
import com.example.tudeeapp.presentation.utills.ShowLoading
import com.example.tudeeapp.presentation.utills.localizeNumbers
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    val themeMode = LocalThemeState.current

    HomeScreenContent(
        state = state,
        onToggleTheme = { isDark -> homeViewModel.onToggledAction(isDark, themeMode) },
        onFloatingActionButtonClick = homeViewModel::onFloatingActionButtonClick,
        onTasksCountClick = homeViewModel::onTasksCountClick,
        onTaskClick = homeViewModel::onTaskClick,
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
            AppHeader(
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
                        contentDescription = stringResource(R.string.add_task)
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
                        ShowError(
                            modifier = Modifier
                                .fillMaxSize(),
                            errorMessage = state.error
                        )
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

@Composable
private fun HomeContent(
    state: HomeUiState,
    onTasksCountClick: (String) -> Unit,
    onTaskClick: (Long) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        item {
            OverViewSection(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .animateItem(),
                state = state,
            )
        }

        item {
            AnimatedVisibility(
                modifier = Modifier
                    .background(Theme.colors.surfaceColors.surface)
                    .padding(horizontal = 16.dp)
                    .animateItem(),
                visible = state.isTasksEmpty,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    EmptyTasksSection(
                        title = stringResource(R.string.no_tasks_for_today),
                        modifier = Modifier
                            .padding(top = 70.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        item {
            AnimatedVisibility(
                modifier = Modifier.animateItem(),
                visible = state.inProgressTasks.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                HomeTaskSection(
                    tasks = state.inProgressTasks,
                    tasksType = TaskStatus.IN_PROGRESS,
                    onTasksCountClick = onTasksCountClick,
                    onTaskClick = onTaskClick
                )
            }
        }

        item {
            AnimatedVisibility(
                modifier = Modifier.animateItem(),
                visible = state.toDoTasks.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                HomeTaskSection(
                    tasks = state.toDoTasks,
                    tasksType = TaskStatus.TO_DO,
                    onTasksCountClick = onTasksCountClick,
                    onTaskClick = onTaskClick
                )
            }
        }

        item {
            AnimatedVisibility(
                modifier = Modifier.animateItem(),
                visible = state.doneTasks.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                HomeTaskSection(
                    tasks = state.doneTasks,
                    tasksType = TaskStatus.DONE,
                    onTasksCountClick = onTasksCountClick,
                    onTaskClick = onTaskClick
                )
            }
        }

    }
}


@Composable
private fun OverViewSection(
    modifier: Modifier = Modifier,
    state: HomeUiState,
) {
    val todayText = stringResource(id = R.string.date_format_today)

    val formattedDate = remember {
        getLocalizedToday().localizeNumbers()
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
            state = state
        )
        OverViewContainer(
            toDoTasksCount = state.toDoTasks.size,
            inProgressTasksCount = state.inProgressTasks.size,
            doneTasksCount = state.doneTasks.size
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
