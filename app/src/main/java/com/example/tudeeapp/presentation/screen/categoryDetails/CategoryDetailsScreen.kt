package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.data.mapper.DataConstant.toResDrawables
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskCard
import com.example.tudeeapp.presentation.common.components.TopAppBar
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.categoryDetails.state.CategoryUiState
import com.example.tudeeapp.presentation.screen.categoryDetails.state.TaskUiState
import com.example.tudeeapp.presentation.screen.home.composable.HomeEmptyTasksSection
import com.example.tudeeapp.presentation.utills.toStyle
import com.example.tudeeapp.presentation.utills.toUi
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedState by viewModel.stateFilter.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage.isNotEmpty() -> Text(text = "error ${uiState.errorMessage}")
        uiState.categoryUiState != null -> {
            CategoryDetailsContent(
                tasks = uiState.taskUiState,
                selectedState = selectedState,
                onStatusChange = viewModel::setStatus,
                onBack = { navController.popBackStack() },
                categoryTitle = uiState.categoryUiState!!.title,
                onOptionClick = { navController.navigate(Screens.CategoryFormEditScreen(uiState.categoryUiState!!.id)) },
                categoryImage = rememberCategoryPainter(uiState.categoryUiState!!),
                topBarOption = editableCategory(uiState.categoryUiState!!)
            )
        }
    }
}

@Composable
private fun rememberCategoryPainter(categoryUiState: CategoryUiState) =
    if (categoryUiState.isPredefined) {
        painterResource(categoryUiState.imageUrl.toResDrawables())
    } else {
        rememberAsyncImagePainter(categoryUiState.imageUrl)
    }

@Composable
private fun editableCategory(categoryUiState: CategoryUiState) = !categoryUiState.isPredefined

@Composable
fun CategoryDetailsContent(
    tasks: List<TaskUiState>,
    selectedState: TaskStatus,
    categoryImage: Painter,
    topBarOption: Boolean,
    modifier: Modifier = Modifier,
    onStatusChange: (TaskStatus) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: () -> Unit = {},
    navController: NavHostController = LocalNavController.current
) {
    Column(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        TopAppBar(
            onClickBack = onBack,
            title = categoryTitle,
            withOption = topBarOption,
            showIndicator = false,
            onclickOption = onOptionClick,
            iconButton = ImageVector.vectorResource(R.drawable.ic_pencil_edit),
            modifier = Modifier.background(Theme.colors.surfaceColors.surfaceHigh)
        )

        val inProgressCount = tasks.count { it.status == TaskStatus.IN_PROGRESS.name }
        val toDoCount = tasks.count { it.status == TaskStatus.TO_DO.name }
        val doneCount = tasks.count { it.status == TaskStatus.DONE.name }

        HorizontalTabs(
            tabs = listOf(
                Tab(title = stringResource(R.string.in_progress_text), count = inProgressCount),
                Tab(title = stringResource(R.string.to_do), count = toDoCount),
                Tab(title = stringResource(R.string.done), count = doneCount)
            ),
            selectedTabIndex = when (selectedState) {
                TaskStatus.IN_PROGRESS -> 0
                TaskStatus.TO_DO -> 1
                TaskStatus.DONE -> 2
            },
            onTabSelected = {
                val selectedStatus = when (it) {
                    0 -> TaskStatus.IN_PROGRESS
                    1 -> TaskStatus.TO_DO
                    2 -> TaskStatus.DONE
                    else -> TaskStatus.IN_PROGRESS
                }
                onStatusChange(selectedStatus)
            }
        )
        val filteredTasks = tasks.filter { it.status == selectedState.name }
        if (filteredTasks.isEmpty()){

            Box(
                modifier = Modifier.fillMaxSize()
            ){
                HomeEmptyTasksSection(
                    title = stringResource(R.string.no_tasks_for_today),
                    modifier = Modifier.align(Alignment.Center)
                )
            }


        }else {
            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredTasks) { task ->
                    val style = TaskPriority.valueOf(task.priority).toUi().toStyle()
                    TaskCard(
                        icon = categoryImage,
                        title = task.title,
                        date = task.createdDate,
                        subtitle = task.description,
                        priorityLabel = task.priority,
                        priorityIcon = painterResource(id = style.iconRes),
                        priorityColor = style.backgroundColor,
                        isDated = true,
                        onClickItem = { navController.navigate(Screens.TaskDetails(task.id)) }
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailsPreview() {
    val fakeTasks = listOf(
        TaskUiState(
            id = 1,
            title = "Study Kotlin",
            description = "Read about coroutines and Flow",
            createdDate = "2025-06-16",
            status = TaskStatus.IN_PROGRESS.name,
            priority = TaskPriority.HIGH.name,
            categoryId = 1L
        ),
        TaskUiState(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14",
            status = TaskStatus.DONE.name,
            priority = TaskPriority.MEDIUM.name,
            categoryId = 1L
        ),
        TaskUiState(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14",
            status = TaskStatus.DONE.name,
            priority = TaskPriority.MEDIUM.name,
            categoryId = 1L
        ),
        TaskUiState(
            id = 3,
            title = "Study Kotlin",
            description = "Read about coroutines and Flow",
            createdDate = "2025-06-16",
            status = TaskStatus.IN_PROGRESS.name,
            priority = TaskPriority.HIGH.name,
            categoryId = 1L
        ),
        TaskUiState(
            id = 4,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14",
            status = TaskStatus.TO_DO.name,
            priority = TaskPriority.MEDIUM.name,
            categoryId = 1L
        )
    )

    val selectedStatus = remember { mutableStateOf(TaskStatus.TO_DO) }

    val fakeNavController = rememberNavController()

    CategoryDetailsContent(
        modifier = Modifier.statusBarsPadding(),
        tasks = fakeTasks,
        selectedState = selectedStatus.value,
        onStatusChange = {
            selectedStatus.value = it
        },
        onBack = {},
        categoryTitle = "Coding",
        categoryImage = painterResource(R.drawable.ic_education),
        navController = fakeNavController,
        topBarOption = true
    )
}