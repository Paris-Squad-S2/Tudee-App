package com.example.tudeeapp.presentation.screen.categoriesForm

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.TextField
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.screen.categoriesForm.components.CategoriesBottomSheetButtons

@Composable
fun AddCategoryScreen(
    viewModel: CategoryFormViewModel
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.updateImage(it) }
    }

    var showSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { showSheet = true }) {
            Text("Open Bottom Sheet")
        }

        AddCategoryScreenContent(
            state = state,
            showSheet = showSheet,
            onDismissSheet = { showSheet = false },
            onSubmit = {
                viewModel.submitCategory()
                viewModel.resetForm()
                showSheet = false
            },
            onCancel = {
                showSheet = false
            },
            onValueChange = viewModel::updateCategoryName,
            onImageClick = {
                imagePickerLauncher.launch("image/*")
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
    onImageClick: () -> Unit,
    showSheet: Boolean,
    onDismissSheet: () -> Unit
) {
    TudeeBottomSheet(
        isVisible = showSheet,
        title = "Add new Category",
        onDismiss = onDismissSheet,
        isScrollable = true,
        skipPartiallyExpanded = true,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = state.categoryName,
                onValueChange = onValueChange,
                placeholder = "Category title",
                leadingIcon = R.drawable.menu_circle,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Category image",
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
                            painter = rememberAsyncImagePainter(state.imageUri),
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
                                painter = painterResource(id = R.drawable.pencil_edit_01),
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
                                painter = painterResource(id = R.drawable.image_add_02),
                                contentDescription = "Upload",
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                            )
                            Text(
                                "Upload",
                                style = Theme.textStyle.label.medium,
                                color = Theme.colors.text.hint
                            )
                        }
                    }
                }
                /*Box(
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
                        .clickable { onImageClick() }, contentAlignment = Alignment.Center
                ) {
                    if (state.imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(state.imageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(1.dp)
                                .clip(RoundedCornerShape(16.dp)),
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
                                painter = painterResource(id = R.drawable.pencil_edit_01),
                                contentDescription = "Edit",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    if (state.imageUri == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.image_add_02
                                ),
                                contentDescription = "Upload",
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Text(
                                "Upload",
                                style = Theme.textStyle.label.medium,
                                color = Theme.colors.text.hint
                            )
                        }
                    }
                }*/
            }
            Spacer(modifier = Modifier.height(12.dp))
            CategoriesBottomSheetButtons(
                state = state,
                onSubmit = onSubmit,
                onCancel = onCancel,
                buttonText = "Add"
            )
        }
    }
}

/*@Composable
fun AddCategoryScreenContent(
    state: CategoryFormState,
    showSheet: Boolean,
    onDismissSheet: () -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit
) {
    TudeeBottomSheet(
        isVisible = showSheet,
        title = "Add new Category",
        onDismiss = onDismissSheet,
        isScrollable = true,
        skipPartiallyExpanded = true,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = state.categoryName,
                onValueChange = onValueChange,
                placeholder = "Category title",
                leadingIcon = R.drawable.menu_circle,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Category image",
                    style = Theme.textStyle.title.medium,
                    modifier = Modifier.align(Alignment.Start),
                    color = Theme.colors.text.title
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(113.dp)
                        .clickable { onImageClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .dashedBorder(
                                strokeWidth = 1.dp,
                                color = Theme.colors.stroke,
                                cornerRadius = 16.dp,
                                dashLength = 6.dp,
                                gapLength = 4.dp
                            )
                            .clip(RoundedCornerShape(16.dp))
                    )

                    if (state.imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(state.imageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(16.dp)),
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
                                painter = painterResource(id = R.drawable.pencil_edit_01),
                                contentDescription = "Edit",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                    if (state.imageUri == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.image_add_02),
                                contentDescription = "Upload",
                                modifier = Modifier.padding(bottom = 12.dp),
                                tint = Color.Unspecified
                            )
                            Text(
                                "Upload",
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
                buttonText = "Add"
            )
        }
    }
}*/




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
