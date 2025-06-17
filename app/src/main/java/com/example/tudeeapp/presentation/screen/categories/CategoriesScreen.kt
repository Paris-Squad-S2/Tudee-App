package com.example.tudeeapp.presentation.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.CategoryItem
import com.example.tudeeapp.presentation.common.components.TextTopBar
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.Screens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    CategoriesContent(
        state = state,
        onClickCategory = {navController.navigate(Screens.CategoryDetails(it))},
        onClickAddCategory = {navController.navigate(Screens.CategoriesForm)}
        )
}

@Composable
fun CategoriesContent(
    state: CategoryUIState,
    onClickCategory: (Long) -> Unit,
    onClickAddCategory: () -> Unit
) {
    TudeeScaffold(
        floatingActionButton = {
            TudeeButton(
                modifier = Modifier.size(64.dp)
                , onClick = {onClickAddCategory()},
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_category),
                        contentDescription = null,
                        tint = Color.Unspecified)
                },
                variant = ButtonVariant.FloatingActionButton,
                )
        },
        topBar = {
            TextTopBar(title = stringResource(R.string.categories))
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage,
                        style = Theme.textStyle.body.medium,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(104.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.colors.surfaceColors.surface),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        items(state.categories) {
                            CategoryListItem(category = it, onClickItem = onClickCategory)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryListItem(
    category: CategoryItemUIState,
    onClickItem: (id: Long) -> Unit
) {
    CategoryItem(
        icon = painterResource(category.imageResId),
        label = category.name,
        count = category.count,
        iconColor = Color.Unspecified,
        isSelected = false,
        modifier = Modifier.clickable { onClickItem(category.id) }
    )
}

