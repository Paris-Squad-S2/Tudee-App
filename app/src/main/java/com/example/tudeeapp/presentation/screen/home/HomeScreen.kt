package com.example.tudeeapp.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.example.tudeeapp.presentation.screen.home.composable.HomeTaskCard
import com.example.tudeeapp.presentation.screen.home.composable.HomeTaskSection
import com.example.tudeeapp.presentation.screen.home.composable.OverviewCard
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    var toggled by remember { mutableStateOf(false) }
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()

    TudeeScaffold(
        topBar = {
            Header(
                modifier = Modifier
                    .background(Theme.colors.primary)
                    .statusBarsPadding(),
                isDarkMode = toggled,
                onToggleTheme = { toggled = it },
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
        content = { snakeBar ->
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Theme.colors.primary)
                        .align(Alignment.TopCenter)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = Theme.colors.surfaceColors.surfaceHigh,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                ) {
                    item {
                        OverViewSection()
                    }
                    item {
                        AnimatedVisibility(
                            visible = state.isTasksEmpty,
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
                    item {
                        if (state.inProgressTasks.isNotEmpty()) {
                            HomeTaskSection(
                                tasks = state.inProgressTasks,
                                tasksType = TaskStatus.IN_PROGRESS,
                                onTasksCount = {} // handle the tasks count
                            )
                        }
                    }
                    item {
                        FlowRow(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            maxItemsInEachRow = 2,
                            maxLines = 2
                        ) {
                            state.inProgressTasks.forEach { task ->
                                HomeTaskCard(
                                    modifier = Modifier
                                        .width(320.dp)
                                        .height(111.dp)
                                        .clickable { /* TudeeBottomSheet details */ },
                                    onPriorityClick = { /*  priorityBottomSheet */ },
                                    task = task
                                )
                            }
                        }
                    }
                    item {
                        if (state.toDoTasks.isNotEmpty()) {
                            HomeTaskSection(
                                tasks = state.toDoTasks,
                                tasksType = TaskStatus.TO_DO,
                                onTasksCount = { } // handle the tasks count
                            )
                        }
                    }
                    item {
                        FlowRow(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            maxItemsInEachRow = 2,
                            maxLines = 2
                        ) {
                            state.toDoTasks.forEach { task ->
                                HomeTaskCard(
                                    modifier = Modifier
                                        .width(320.dp)
                                        .height(111.dp)
                                        .clickable { /* TudeeBottomSheet details */ },
                                    onPriorityClick = { /*  priorityBottomSheet */ },
                                    task = task
                                )
                            }
                        }
                    }
                    item {
                        if (state.doneTasks.isNotEmpty()) {
                            HomeTaskSection(
                                tasks = state.doneTasks,
                                tasksType = TaskStatus.DONE,
                                onTasksCount = { } // handle the tasks count
                            )
                        }
                    }
                    item {
                        FlowRow(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            maxItemsInEachRow = 2,
                            maxLines = 2
                        ) {
                            state.doneTasks.forEach { task ->
                                HomeTaskCard(
                                    modifier = Modifier
                                        .width(320.dp)
                                        .height(111.dp)
                                        .clickable { /* TudeeBottomSheet details */ },
                                    onPriorityClick = { /*  priorityBottomSheet */ },
                                    task = task
                                )
                            }
                        }
                    }
                }
            }
        },
    )
}


@Composable
fun EmptyTasksSection(modifier: Modifier) {
    Box(
        modifier = modifier,
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
                painter = painterResource(R.drawable.img_ropot4),
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
                .padding(end = 100.dp)
                .width(203.dp)
                .height(74.dp)
                .offset(x = 0.dp, y = (-10).dp)
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
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.no_tasks_for_today),
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.hint
            )
            Text(
                text = stringResource(R.string.tap_the_button_to_add_your_first_one),
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.hint
            )
        }

    }
}


@Composable
private fun OverViewSection(modifier: Modifier = Modifier) {
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
                contentDescription = "Today Date",
                tint = Theme.colors.text.body,
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "today, 22 Jun 2025",
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
            title = "Stay working!",
            description = "You've completed 3 out of 10 tasks Keep going!",
            image = painterResource(id = R.drawable.img_ropot1),
            titleIcon = painterResource(id = R.drawable.ic_good)
        )
        OverViewContainer()
    }
}

@Composable
fun OverViewContainer(modifier: Modifier = Modifier) {
    Column(
        modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            text = "Overview", modifier = Modifier.padding(bottom = 8.dp),
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title
        )
        OverViewCardsRow()
    }

}

@Composable
private fun OverViewCardsRow() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.greenAccent,
            painter = painterResource(id = R.drawable.ic_done),
            stat = 2,
            label = "Done"
        )
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.yellowAccent,
            painter = painterResource(id = R.drawable.ic_in_progress),
            stat = 16,
            label = "In Progress",
        )
        OverviewCard(
            modifier = Modifier.weight(1f),
            color = Theme.colors.status.purpleAccent,
            painter = painterResource(id = R.drawable.ic_to_do),
            stat = 1,
            label = "To-Do",
        )
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}
