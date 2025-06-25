package com.example.tudeeapp.presentation.screen.categoriesForm

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ConfirmationDialogBox
import com.example.tudeeapp.presentation.common.components.TextField
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.extentions.dashedBorder
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.LocalSnackBarState
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheetWithPinnedFooter
import com.example.tudeeapp.presentation.screen.categoriesForm.components.CategoriesBottomSheetButtons
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CategoryForm(
    viewModel: CategoryFormViewModel = koinViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isEdit = state.categoryId != 0L
    val snackbarHostState = LocalSnackBarState.current
    val context = LocalContext.current
    var showDeleteConfirmation by remember { mutableStateOf(false) }


    LaunchedEffect(state.successMessage, state.errorMessage) {
        state.successMessage?.let {
            snackbarHostState.show(message =context.getString(it) , isSuccess = true)
            viewModel.onCancel()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.updateImage(it)
        }
    }

    TudeeBottomSheetWithPinnedFooter(
        showBottomSheet = true,
        isScrollable = true,
        isDraggable = true,
        skipPartiallyExpanded = false,
        title = if (isEdit) stringResource(id = R.string.editCategory) else stringResource(id = R.string.addnewCategory),
        headerEnd = {
            if (isEdit) {
                DeleteButton(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = { showDeleteConfirmation = true }
                )
            }
        },
        onDismiss = {
            viewModel.onCancel()
        },
        stickyBottomContent = {
            CategoriesBottomSheetButtons(
                modifier = Modifier.padding(bottom = 40.dp),
                state = state,
                onSubmit = {
                    viewModel.submitCategory()
                },
                onCancel = {
                    viewModel.onCancel()
                },
                buttonText = if (isEdit) stringResource(id = R.string.save)
                else stringResource(id = R.string.add)
            )
        }
    ) {
        CategoryFormContent(
            state = state,
            onValueChange = viewModel::updateCategoryName,
            onImageClick = {
                imagePickerLauncher.launch(arrayOf("image/*"))
            },
        )
    }
    if (showDeleteConfirmation) {
        TudeeBottomSheet(
            showBottomSheet = true,
            title = stringResource(id = R.string.delete_category),
            onDismiss = {
                showDeleteConfirmation = false
            }
        ) {
            ConfirmationDialogBox(
                title = R.string.are_you_sure_to_continue,
                onConfirm = {
                    viewModel.deleteCategory()
                    showDeleteConfirmation = false
                    snackbarHostState.show(context.getString(R.string.deleted_successfully), isSuccess = true)
                },
                onDismiss = {
                    showDeleteConfirmation = false
                }
            )
        }
    }
}

@Composable
fun CategoryFormContent(
    state: CategoryFormUIState,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = state.categoryName,
            onValueChange = onValueChange,
            placeholder = stringResource(id = R.string.categoryTitle),
            leadingIcon = R.drawable.ic_menu_circle,
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.categoryImage),
                style = Theme.textStyle.title.medium,
                modifier = Modifier.align(Alignment.Start).padding(8.dp),
                color = Theme.colors.text.title,
            )
            Box(
                modifier = Modifier
                    .size(113.dp)
                    .dashedBorder(
                        strokeWidth = 1.dp,
                        color = Theme.colors.stroke,
                        cornerRadius = 16.dp,
                        dashLength = 6.dp,
                        gapLength = 6.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onImageClick() },
                contentAlignment = Alignment.Center
            ) {
                if (state.imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = state.imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .padding(1.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(40))
                            .background(Theme.colors.surfaceColors.surfaceHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pencil_edit_01),
                            contentDescription = "Edit",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image_add_02),
                            contentDescription = "Upload",
                            modifier = Modifier.padding(bottom = 12.dp),
                            tint = Theme.colors.text.hint
                        )
                        Text(
                            stringResource(id = R.string.upload),
                            style = Theme.textStyle.label.medium,
                            color = Theme.colors.text.hint
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    TudeeButton(
        onClick = onClick,
        text = stringResource(R.string.delete),
        variant = ButtonVariant.TextButton,
        isNegative = true
    )
}
