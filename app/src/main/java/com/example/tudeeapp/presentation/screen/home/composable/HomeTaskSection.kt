package com.example.tudeeapp.presentation.screen.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.home.TaskUi

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeTaskSection(
    modifier: Modifier = Modifier,
    tasks: List<TaskUi>,
    tasksType: TaskStatus,
    onTasksCountClick: () -> Unit,
    onTaskClick: (Long) -> Unit,
) {


    val sectionTitle = when (tasksType) {
        TaskStatus.IN_PROGRESS -> stringResource(R.string.in_progress)
        TaskStatus.TO_DO -> stringResource(R.string.to_do)
        TaskStatus.DONE -> stringResource(R.string.done)
    }

    Column(
        modifier = modifier
            .padding(top = 14.dp)
            .background(Theme.colors.surfaceColors.surface)
    ) {
        Row(
            modifier = modifier
                .padding(top = 12.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)

                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sectionTitle,
                style = Theme.textStyle.title.large,
                color = Theme.colors.text.title,

                )
            Box(
                modifier = Modifier
                    .width(45.dp)
                    .height(28.dp)
                    .background(
                        color = Theme.colors.surfaceColors.surfaceHigh,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .clip(RoundedCornerShape(100.dp))
                    .clickable { onTasksCountClick() }

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = tasks.size.toString(),
                        style = Theme.textStyle.label.medium,
                        color = Theme.colors.text.body
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow),
                        contentDescription = "arrow",
                        tint = Theme.colors.text.body
                    )
                }
            }
        }
        FlowRow(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 2,
            maxLines = 2,
        ) {
            tasks.forEach { task ->
                HomeTaskCard(
                    modifier = Modifier
                        .width(320.dp)
                        .height(111.dp)
                        .clickable {
                            onTaskClick(task.id)
                        },
                    task = task
                )
            }
        }
    }
}
