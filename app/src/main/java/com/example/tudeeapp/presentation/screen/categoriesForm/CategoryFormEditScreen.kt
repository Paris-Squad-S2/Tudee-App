package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonState
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TextField
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CategoryFormEditScreen(viewModel: CategoryFormViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateImage(it)
        }
    }

    CategoryFormEditContent(
        state = state,
        onCancel = {
            navController.popBackStack()
        },
        onSubmit = {
            navController.popBackStack()
        },
        onTitleChange = viewModel::updateCategoryName,
        onImageClick = {
            imagePickerLauncher.launch("image/*")
        },
        onSubmitEdit = viewModel::editCategory,
        onDismiss = { navController.popBackStack() }
    )
}


@Composable
fun CategoryFormEditContent(
    state: CategoryFormState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    onTitleChange: (String) -> Unit,
    onImageClick: () -> Unit,
    onSubmitEdit: () -> Unit,
    onDismiss: () -> Unit
) {
    var isSheetOpen by remember { mutableStateOf(true) }
    TudeeBottomSheet(
        isVisible = true,
        title = stringResource(R.string.editCategory),
        isScrollable = true,
        skipPartiallyExpanded = true,
        onDismiss = onDismiss,
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        )
                )
                {
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = state.categoryName,
                        onValueChange = onTitleChange,
                        placeholder = "Category title",
                        leadingIcon = R.drawable.menu_circle,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.categoryImage),
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
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 20.dp,
                            ambientColor = Color(0x14000000),
                            spotColor = Color(0x14000000)
                        )
                        .background(
                            Theme.colors.surfaceColors.surfaceHigh
                        )
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        TudeeButton(
                            onClick = {
                                onSubmitEdit()
                                onSubmit()
                            },
                            text = stringResource(R.string.save),
                            variant = ButtonVariant.FilledButton,
                            modifier = Modifier.fillMaxWidth(),
                            state = if (state.isFormValid) ButtonState.Normal else ButtonState.Disabled
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        TudeeButton(
                            onClick = onCancel, text = stringResource(R.string.cancel),
                            variant = ButtonVariant.TextButton,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        var showSnackBar by remember { mutableStateOf(false) }

                        // When showSnackBar is true, hide it after 3 seconds
                        LaunchedEffect(showSnackBar) {
                            if (showSnackBar) {
                                delay(1000) // duration in milliseconds
                                showSnackBar = false
                            }
                        }

                    }
                }
            }
        },
    )

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

@Composable
@Preview
fun CategoryFormEditScreenP(){
    CategoryFormEditScreen()
}