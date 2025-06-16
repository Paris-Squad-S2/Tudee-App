package com.example.tudeeapp.presentation.screen.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.components.CategoryItem
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.screen.categories.viewModel.CategoriesViewModel
import com.example.tudeeapp.presentation.screen.categories.viewModel.state.CategoryItemUIState
import com.example.tudeeapp.presentation.screen.categories.viewModel.state.CategoryUIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    CategoriesContent(state = state)
}

@Composable
fun CategoriesContent(state: CategoryUIState) {
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Categories",
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 20.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(state.categories) {
                CategoryListItem(category = it)
            }
        }
    }
}

@Composable
private fun CategoryListItem(category: CategoryItemUIState) {
    CategoryItem(
        icon = painterResource(category.imageResId),
        label = category.name,
        count = category.count,
        iconColor = Theme.colors.secondary,
        isSelected = false
    )
}