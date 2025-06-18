package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskCard
import com.example.tudeeapp.presentation.common.components.TopAppBar
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.errorScreen.ErrorScreen
import com.example.tudeeapp.presentation.screen.loadingScreen.LoadingScreen
import com.example.tudeeapp.presentation.utills.toStyle
import com.example.tudeeapp.presentation.utills.toUi
import kotlinx.datetime.toLocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel()
) {
    val navController = LocalNavController.current

    val uiState by viewModel.category.collectAsState()
    val selectedState by viewModel.stateFilter.collectAsState()

    when {
        uiState.isLoading -> {
            LoadingScreen()
        }

        uiState.errorMessage.isNotEmpty() -> {
            ErrorScreen(message = uiState.errorMessage)
        }

        uiState.category != null -> {
            CategoryDetailsContent(
                tasks = uiState.tasks,
                selectedState = selectedState,
                onStatusChange = viewModel::setStatus,
                onBack = { navController.popBackStack() },
                categoryTitle = uiState.category!!.title,
                onOptionClick = { navController.navigate(Screens.CategoriesForm) },
                categoryImage = uiState.category!!.imageUrl.toInt()
            )
        }
    }
}

@Composable
fun CategoryDetailsContent(
    tasks: List<Task>,
    selectedState: TaskStatus,
    categoryImage: Int,
    modifier: Modifier = Modifier,
    onStatusChange: (TaskStatus) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: () -> Unit = {},
    navController: NavHostController = LocalNavController.current
) {
    Column(modifier = modifier.fillMaxSize().statusBarsPadding()) {
        TopAppBar(
            modifier = Modifier,
            onClickBack = onBack,
            title = categoryTitle,
            withOption = true,
            showIndicator = false,
            onclickOption = onOptionClick,
            iconButton = ImageVector.vectorResource(R.drawable.ic_pencil_edit),
            iconSize = 20.dp
        )

        val inProgressCount = tasks.count { it.status == TaskStatus.IN_PROGRESS }
        val toDoCount = tasks.count { it.status == TaskStatus.TO_DO }
        val doneCount = tasks.count { it.status == TaskStatus.DONE }

        HorizontalTabs(
            tabs = listOf(
                Tab(title = stringResource(R.string.in_progress), count = inProgressCount),
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

        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks.filter { it.status == selectedState }) { task ->
                val style = task.priority.toUi().toStyle()
                TaskCard(
                    icon = painterResource(categoryImage) ,
                    title = task.title,
                    date = task.createdDate.toString(),
                    subtitle = task.description,
                    priorityLabel = task.priority.name,
                    priorityIcon = painterResource(id = style.iconRes),
                    priorityColor = style.backgroundColor,
                    isDated = true,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.TaskDetails(task.id))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailsPreview() {

    val fakeTasks = listOf(
        Task(
            id = 1,
            title = "Study Kotlin",
            description = "Read about coroutines and Flow",
            createdDate = "2025-06-16".toLocalDate(),
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.HIGH,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.IN_PROGRESS,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.TO_DO,
            priority = TaskPriority.LOW,
            categoryId = 1L
        ),
        Task(
            id = 1,
            title = "Study Kotlin",
            description = "Read about coroutines and Flow",
            createdDate = "2025-06-16".toLocalDate(),
            status = TaskStatus.TO_DO,
            priority = TaskPriority.HIGH,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
        Task(
            id = 2,
            title = "Write Report",
            description = "Project update",
            createdDate = "2025-06-14".toLocalDate(),
            status = TaskStatus.DONE,
            priority = TaskPriority.MEDIUM,
            categoryId = 1L
        ),
    )

    val selectedStatus = remember { mutableStateOf(TaskStatus.IN_PROGRESS) }
    CategoryDetailsContent(
        modifier = Modifier.statusBarsPadding(),
        tasks = fakeTasks,
        selectedState = selectedStatus.value,
        onStatusChange = {
            selectedStatus.value = it
        },
        onBack = {},
        categoryTitle = "Coding",
        categoryImage = R.drawable.ic_education
    )
}