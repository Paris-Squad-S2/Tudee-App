package com.example.tudeeapp.presentation.bottomSheets.addEditTask.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.Category
import com.example.tudeeapp.presentation.common.components.CategoryItem
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun CategoryGrid(categories: List<Category>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.category),
            style = Theme.textStyle.title.medium,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    icon = painterResource(category.image),
                    label = category.title,
                    iconColor = Color.Unspecified,
                    isSelected = category.isSelected
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
            Category(image = R.drawable.ic_education, title = "Work"),
            Category(image = R.drawable.ic_adoration, title = "Personal" , isSelected = true),
            Category(image = R.drawable.ic_gym, title = "Shopping"),
            Category(image = R.drawable.ic_education, title = "Work"),
            Category(image = R.drawable.ic_adoration, title = "Personal"),
            Category(image = R.drawable.ic_gym, title = "Shopping"),
            Category(image = R.drawable.ic_education, title = "Work"),
            Category(image = R.drawable.ic_adoration, title = "Personal"),
            Category(image = R.drawable.ic_gym, title = "Shopping"),
        )
        CategoryGrid(categories = sampleCategories)
    }
}