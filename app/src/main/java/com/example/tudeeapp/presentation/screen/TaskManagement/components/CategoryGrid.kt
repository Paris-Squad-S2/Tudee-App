package com.example.tudeeapp.presentation.screen.TaskManagement.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.screen.TaskManagement.CategoryUiState
import com.example.tudeeapp.presentation.common.components.CategoryItem
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun CategoryGrid(
    categories: List<CategoryUiState>,
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.category),
            style = Theme.textStyle.title.medium,
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(25.dp),
            maxItemsInEachRow = 3
        ) {
            categories.forEachIndexed { index, category ->
                CategoryItem(
                    icon = category.image,
                    label = category.title,
                    iconColor = Color.Unspecified,
                    isSelected = category.isSelected,
                    isPredefined = category.isPredefined,
                    onClick = { onCategoryClick(index) }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CategoryGridPreview() {
    BasePreview {
        val sampleCategories = listOf(
            CategoryUiState(image = "R.drawable.ic_education", title = "Work"),
            CategoryUiState(
                image = "R.drawable.ic_adoration",
                title = "Personal",
                isSelected = true
            ),
            CategoryUiState(image = "R.drawable.ic_gym", title = "Shopping"),
            CategoryUiState(image = "R.drawable.ic_education", title = "Work"),
            CategoryUiState(image = "R.drawable.ic_adoration", title = "Personal"),
            CategoryUiState(image = "R.drawable.ic_gym", title = "Shopping"),
            CategoryUiState(image = "R.drawable.ic_education", title = "Work"),
            CategoryUiState(image = "R.drawable.ic_adoration", title = "Personal"),
            CategoryUiState(image = "R.drawable.ic_gym", title = "Shopping"),
        )
        CategoryGrid(categories = sampleCategories)
    }
}