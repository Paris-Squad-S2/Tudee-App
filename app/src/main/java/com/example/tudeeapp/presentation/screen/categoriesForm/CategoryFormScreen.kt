package com.example.tudeeapp.presentation.screen.categoriesForm

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.screen.categoriesForm.components.CategoryBottomSheetContent

@Composable
fun CategoryFormScreen(
    viewModel: CategoryFormViewModel
){
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    CategoryFormScreenContent(
        state = state,
        onDismissRequest = {
            viewModel.dismissForm()
        },
        onCancel = {
            navController.popBackStack()
        },
        onSubmit = {
            viewModel.submitCategory()
            navController.popBackStack()
        },
        onTitleChange = viewModel::updateCategoryName,
        onImageClick = {}
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFormScreenContent(
    state: CategoryFormState,
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    onTitleChange: (String) -> Unit,
    onImageClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (state.isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Theme.colors.surfaceColors.surfaceHigh,
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            )
        ) {
            CategoryBottomSheetContent(
                state = state,
                onCancel = onCancel,
                onSubmit = onSubmit,
                onTitleChange = onTitleChange,
                onImageClick = onImageClick
            )
        }
    }
}


