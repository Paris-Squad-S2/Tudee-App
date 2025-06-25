package com.example.tudeeapp.presentation.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.CategoryItem
import com.example.tudeeapp.presentation.common.components.TextTopBar
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.utills.ShowError
import com.example.tudeeapp.presentation.utills.ShowLoading
import com.example.tudeeapp.presentation.utills.toPainter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    CategoriesContent(
        state = state,
        onClickCategory = { navController.navigate(Destinations.CategoryDetails(it)) },
        onClickAddCategory = {
            navController.navigate(Destinations.CategoryForm())
        }
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
                modifier = Modifier.size(64.dp),
                onClick = { onClickAddCategory() },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_category),
                        contentDescription = stringResource(R.string.addnewCategory),
                        tint = Color.Unspecified
                    )
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
                    ShowLoading()
                }

                state.errorMessage != null -> {
                    ShowError(
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = state.errorMessage,
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
    val painter: Painter = toPainter(category.isPredefined, category.imageUri)

    CategoryItem(
        icon = painter,
        label = category.name,
        count = category.count,
        isSelected = false,
        onClick = { onClickItem(category.id) },
        isPredefined = category.isPredefined
    )
}

