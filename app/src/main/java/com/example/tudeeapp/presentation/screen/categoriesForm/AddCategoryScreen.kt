
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.TextField
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.LocalSnackBarState
import com.example.tudeeapp.presentation.screen.categoriesForm.components.CategoriesBottomSheetButtons
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddCategoryScreen(
    viewModel: CategoryFormViewModel = koinViewModel()
) {

    val formState by viewModel.state.collectAsState()
    var showSheet by remember { mutableStateOf(true) }
    val navController = LocalNavController.current
    val context = LocalContext.current
    val snackbarHostState = LocalSnackBarState.current

    LaunchedEffect(formState.successMessage, formState.errorMessage) {
        formState.successMessage?.let {
            snackbarHostState.show(message = it, isSuccess = true)
            navController.popBackStack()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.updateImage(it)
        }
    }

    TudeeBottomSheet(
        isVisible = showSheet,
        title = stringResource(id = R.string.addnewCategory),
        onDismiss = {
            showSheet = false
            navController.popBackStack()
        },
        isScrollable = true,
        skipPartiallyExpanded = true
    ) {
        AddCategoryScreenContent(
            state = formState,
            onCancel = {
                showSheet = false
                navController.popBackStack()
            },
            onSubmit = {
                viewModel.addCategory()
            },
            onValueChange = { viewModel.updateCategoryName(it) },
            onImageClick = {
                imagePickerLauncher.launch(arrayOf("image/*"))
            }
        )
    }
}


@Composable
fun AddCategoryScreenContent(
    state: CategoryFormState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit
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
                    modifier = Modifier.align(Alignment.Start),
                    color = Theme.colors.text.title
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(113.dp)
                        .dashedBorder(
                            strokeWidth = 1.dp,
                            color = Theme.colors.stroke,
                            cornerRadius = 16.dp,
                            dashLength = 6.dp,
                            gapLength = 4.dp
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
                    }
                    if (state.imageUri == null) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_image_add_02),
                                contentDescription = "Upload",
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                            )
                            Text(
                                stringResource(id = R.string.categoryImage),
                                style = Theme.textStyle.label.medium,
                                color = Theme.colors.text.hint
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            CategoriesBottomSheetButtons(
                state = state,
                onSubmit = onSubmit,
                onCancel = onCancel,
                buttonText = stringResource(id = R.string.add)
            )
        }
}



fun Modifier.dashedBorder(
    strokeWidth: Dp,
    color: Color,
    cornerRadius: Dp = 0.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 10.dp
): Modifier = this.then(
    Modifier.drawWithCache {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        )
        onDrawBehind {
            drawRoundRect(
                color = color,
                size = size,
                style = stroke,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
)