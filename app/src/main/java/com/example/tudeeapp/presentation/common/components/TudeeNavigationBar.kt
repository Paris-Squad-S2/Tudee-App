package com.example.tudeeapp.presentation.common.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.Destination
import com.example.tudeeapp.presentation.navigation.Navigator
import com.example.tudeeapp.presentation.navigation.NavigatorImpl
import com.example.tudeeapp.presentation.navigation.Destinations
import kotlinx.coroutines.launch


@Composable
fun TudeeNavigationBar(
    navHostController: NavHostController,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()

    val selectedDestinationIndex by remember(currentBackStackEntry) {
        derivedStateOf {
            TudeeNavBarItem.destinations.indexOfFirst { item ->
                currentBackStackEntry?.destination?.hasRoute(item.destination::class) == true
            }.coerceAtLeast(0)
        }
    }

    val isVisible by remember {
        derivedStateOf {
            TudeeNavBarItem.destinations.any {
                currentBackStackEntry?.destination?.hasRoute(it.destination::class) == true
            }
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
    ) {
        NavigationBar(containerColor = Theme.colors.surfaceColors.surfaceHigh, modifier = modifier) {
            TudeeNavBarItem.destinations.forEachIndexed { index, item ->
                TudeeNavBarItem(
                    currentIndex = index,
                    selectedDestinationIndex = selectedDestinationIndex,
                    currentItem = item,
                    onItemClick = {
                        scope.launch {
                            navigator.navigate(item.destination)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.TudeeNavBarItem(
    currentIndex: Int,
    selectedDestinationIndex: Int,
    currentItem: TudeeNavBarItem,
    onItemClick: () -> Unit
) {
    val selected = selectedDestinationIndex == currentIndex
    Box(
        modifier = Modifier
            .weight(1f)
            .clickable(
                enabled = !selected,
                onClick = onItemClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .then(
                if (selected) Modifier.background(Theme.colors.surfaceColors.surfaceHigh) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        TudeeNavBarIcon(
            currentItem = currentItem,
            selected = selected
        )
    }
}

@Composable
private fun TudeeNavBarIcon(
    currentItem: TudeeNavBarItem,
    selected: Boolean = false
) {
    Box(contentAlignment = Alignment.Center) {
        if (selected) {
            Spacer(
                modifier = Modifier
                    .size(42.dp)
                    .background(color = Theme.colors.primaryVariant, shape = RoundedCornerShape(16.dp))
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(currentItem.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (selected) Theme.colors.primary else Theme.colors.text.hint
        )
    }
}

sealed class TudeeNavBarItem(
    @DrawableRes val icon: Int,
    val destination: Destination,
) {
    data object Home : TudeeNavBarItem(
        icon = R.drawable.ic_selected_home,
        destination = Destinations.Home
    )

    data object Tasks : TudeeNavBarItem(
        icon = R.drawable.ic_selected_task,
        destination = Destinations.Task()
    )

    data object Categories : TudeeNavBarItem(
        icon = R.drawable.ic_selected_categories,
        destination = Destinations.Category
    )

    companion object {
        val destinations = listOf(Home, Tasks, Categories)
    }
}


@Preview
@Composable
private fun TudeeNavigationBarPreview() {
    BasePreview {
        TudeeNavigationBar(
            navHostController = rememberNavController(),
            navigator = NavigatorImpl(startGraph = Destinations.TudeeGraph),
        )
    }
}
