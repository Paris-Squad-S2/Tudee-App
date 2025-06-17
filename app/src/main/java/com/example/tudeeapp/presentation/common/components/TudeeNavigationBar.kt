package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.Screens

@Preview
@Composable
fun TudeeNavigationBar(
    modifier: Modifier = Modifier,
    itemList: List<NavItem> = navItemList,
    onItemClick: (NavItem) -> Unit = {},
    selected: Int = 0
) {
    var selectedIndex by remember {
        mutableIntStateOf(selected)
    }

    NavigationBar(modifier = modifier.background(Theme.colors.surfaceColors.surfaceHigh)) {
        itemList.forEachIndexed { index, navItem ->
            val isSelected = selectedIndex == index
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent

                ),
                selected = isSelected,
                onClick = {
                    selectedIndex = index
                    onItemClick(navItem)
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                color = if (isSelected) Color(0xFFE5F6FF) else Color.Transparent,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isSelected)
                                    navItem.selectedIcon
                                else navItem.unselectedIcon
                            ),
                            null,
                            tint = Color.Unspecified
                        )
                    }
                },

                )
        }

    }
}

data class NavItem(
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val label: String,
    val screen: Screens
)

val navItemList = listOf(
    NavItem(
        unselectedIcon = R.drawable.ic_unselected_home,
        selectedIcon = R.drawable.ic_selected_home,
        label = "Home",
        Screens.Home
    ),
    NavItem(
        unselectedIcon = R.drawable.ic_unselected_tasks,
        R.drawable.ic_selected_task,
        label = "Tasks",
        Screens.Task
    ),
    NavItem(
        unselectedIcon = R.drawable.ic_unselected_categories,
        R.drawable.ic_selected_categories,
        label = "Categories",
        Screens.Category
    ),
)
