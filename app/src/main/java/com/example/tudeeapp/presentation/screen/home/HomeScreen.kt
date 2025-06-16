package com.example.tudeeapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.Slider
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.home.composable.OverviewCard

@Composable
fun HomeScreen() {
    val navController = LocalNavController.current
    TudeeScaffold(
        showTopBar = true,
        showFloatingActionButton = true,
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
                ) {
                    item {
                        OverViewSection()
                    }
                    item {

                    }

                }
            }
        },
    )
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
