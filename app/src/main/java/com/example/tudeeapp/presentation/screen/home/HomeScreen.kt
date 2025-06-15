package com.example.tudeeapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.Slider
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun HomeScreen(userName: String) {
//    val navController = LocalNavController.current
    TudeeScaffold(
        showTopBar = true,
        showFloatingActionButton = true,
        showBottomBar = true,
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
                ) {
                    item {
                        Column(
                            modifier = Modifier
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
                                image = painterResource(id = R.drawable.ropot1),
                                titleIcon = painterResource(id = R.drawable.good)
                            )
                            OverViewContainer()

                        }
                    }

                }
            }
        },
    )
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
        OverViewCards()
    }

}

@Composable
fun OverViewCards() {
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
fun OverviewCard(
    modifier: Modifier,
    color: Color,
    painter: Painter,
    stat: Int,
    label: String,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 20.dp))
            .background(color = color)
    ) {
        Icon(
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopEnd)
                .offset(x = 39.dp, y = (-39).dp),
            painter = painterResource(R.drawable.ic_overview_card_background),
            contentDescription = "icon",
            tint = Color.Unspecified,
        )
        Column(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, bottom = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x1FFFFFFF),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .size(40.dp)
                    .background(color = Color(0x3DFFFFFF), shape = RoundedCornerShape(size = 12.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painter,
                    contentDescription = "icon",
                    tint = Color.Unspecified
                )
            }
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stat.toString(),
                style = Theme.textStyle.headline.medium,
                color = Theme.colors.surfaceColors.onPrimaryColors.onPrimary
            )
            Text(
                text = label,
                style = Theme.textStyle.label.small,
                color = Theme.colors.surfaceColors.onPrimaryColors.onPrimaryCaption
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen("userName")
}


//                    navController.navigate(
//                        Screens.Task(
//                            taskId = 12,
//                            taskTitle = "taskTitle"
//                        )
//                    )
