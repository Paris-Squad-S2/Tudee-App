package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.ConfirmationDialogBox
import com.example.tudeeapp.presentation.common.components.HorizontalTabs
import com.example.tudeeapp.presentation.common.components.Tab
import com.example.tudeeapp.presentation.common.components.TaskItemWithSwipe
import com.example.tudeeapp.presentation.common.components.TopAppBar
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.mapper.toResDrawables
import com.example.tudeeapp.presentation.LocalSnackBarState
import com.example.tudeeapp.presentation.common.components.EmptyTasksSection
import com.example.tudeeapp.presentation.utills.toStyle
import com.example.tudeeapp.presentation.utills.toUi
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel()
) {
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
                onBack = { viewModel.onClickBack() },
                categoryTitle = uiState.categoryUiState!!.title,
                onOptionClick = {
                    viewModel.onClickEditCategory()
                },
                categoryImage = rememberCategoryPainter(uiState.categoryUiState!!),
                topBarOption = editableCategory(uiState.categoryUiState!!),
                onClickDeleteIcon = viewModel::deleteTask,
                interactionListener = viewModel
            )
        }
    }
}

@Composable
private fun rememberCategoryPainter(categoryUiState: CategoryUiState) =
    if (categoryUiState.isPredefined) {
        painterResource(categoryUiState.imageUri.toResDrawables())
    } else {
        rememberAsyncImagePainter(categoryUiState.imageUri)
    }

@Composable
private fun editableCategory(categoryUiState: CategoryUiState) = !categoryUiState.isPredefined

@Composable
fun CategoryDetailsContent(
    tasks: List<TaskUiState>,
    selectedState: TaskStatus,
    categoryImage: Painter,
    topBarOption: Boolean,
    onClickDeleteIcon: (Long) -> Unit,
    interactionListener: CategoryInteractionListener,
    modifier: Modifier = Modifier,
    onStatusChange: (TaskStatus) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.surfaceColors.surfaceHigh)
            .statusBarsPadding()
    ) {
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
                    .padding(horizontal = 16.dp)
            ){
                EmptyTasksSection(
                    title = stringResource(R.string.no_task_for, categoryTitle),
                    description = stringResource(R.string.add_first_task),
                    modifier = Modifier.align(Alignment.Center)
                )
            }


        } else {
            var taskIdToDelete by remember { mutableStateOf<Long?>(null) }
            val showSnackBar = LocalSnackBarState.current
            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    filteredTasks,
                    key = { task -> task.id }
                ) { task ->
                    val style = TaskPriority.valueOf(task.priority).toUi().toStyle()
                    TaskItemWithSwipe(
                        icon = categoryImage,
                        iconColor = Color.Unspecified,
                        title = task.title,
                        date = task.createdDate,
                        subtitle = task.description,
                        priorityLabel = task.priority,
                        priorityIcon = painterResource(id = style.iconRes),
                        priorityColor = style.backgroundColor,
                        isDated = true,
                        onClickItem = {
                            interactionListener.onTaskClick(task.id)
                        },
                        onDelete = { taskIdToDelete = task.id },
                        modifier = Modifier.animateItem()
                    )
                    if (taskIdToDelete == task.id) {
                        TudeeBottomSheet(
                            showSheet = true,
                            title = LocalContext.current.getString(R.string.delete_task),
                            onDismiss = { taskIdToDelete = null },
                            content = {
                                val context = LocalContext.current

                                ConfirmationDialogBox(
                                    title = R.string.are_you_sure_to_continue,
                                    onConfirm = {
                                        onClickDeleteIcon(task.id)
                                        showSnackBar.show(context.getString(R.string.deleted_task_successfully))
                                        taskIdToDelete = null
                                    },
                                    onDismiss = { taskIdToDelete = null })
                            },
                        )
                    }
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
        topBarOption = true,
        onClickDeleteIcon = {},
        interactionListener = object : CategoryInteractionListener {
            override fun onClickEditCategory() {}
            override fun onClickBack() {}
            override fun onTaskClick(id: Long) {}
        }
    )
}