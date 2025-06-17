//package com.example.tudeeapp.presentation.screen.categoriesForm.components
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithCache
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.geometry.CornerRadius
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.PathEffect
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import coil.compose.rememberAsyncImagePainter
//import com.example.tudeeapp.R
//import com.example.tudeeapp.presentation.common.components.TextField
//import com.example.tudeeapp.presentation.design_system.theme.Theme
//import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormState
//
//
//@Composable
//fun CategoryBottomSheetContent(
//    state: CategoryFormState,
//    onCancel: () -> Unit,
//    onSubmit: () -> Unit,
//    onTitleChange: (String) -> Unit,
//    onImageClick: () -> Unit,
//) {
//    val title = if (state.isEdit) "Edit category" else "Add new category"
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(
//                Theme.colors.surfaceColors.surfaceHigh,
//                shape = RoundedCornerShape(
//                    topStart = 24.dp,
//                    topEnd = 24.dp
//                )
//            )
//    ) {
//        Column(
//            modifier = Modifier
//            .padding(
//                horizontal = 16.dp,
//                vertical = 16.dp
//            )
//        )
//        {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .width(32.dp)
//                    .height(4.dp)
//                    .background(
//                        Theme.colors.text.body.copy(alpha = .4f),
//                        shape = RoundedCornerShape(100)
//                    )
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = title,
//                style = Theme.textStyle.title.large,
//                modifier = Modifier
//                    .align(Alignment.Start)
//                    .padding(bottom = 12.dp)
//            )
//            TextField(
//                value = state.categoryName,
//                onValueChange = onTitleChange,
//                placeholder = "Category title",
//                leadingIcon = R.drawable.menu_circle,
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(
//                text = "Category image",
//                style = Theme.textStyle.title.medium,
//                modifier = Modifier.align(Alignment.Start)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Box(
//                modifier = Modifier
//                    .size(113.dp)
//                    .dashedBorder(
//                        strokeWidth = 1.dp,
//                        color = Theme.colors.stroke,
//                        cornerRadius = 16.dp,
//                        dashLength = 6.dp,
//                        gapLength = 4.dp
//                    )
//                    .clickable { onImageClick() },
//                contentAlignment = Alignment.Center
//            ) {
//                if (state.imageUri == null) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.image_add_02),
//                            contentDescription = "Upload",
//                            modifier = Modifier.padding(bottom = 12.dp)
//                        )
//                        Text("Upload", style = Theme.textStyle.label.medium)
//                    }
//                } else {
//                    Image(
//                        painter = rememberAsyncImagePainter(state.imageUri),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .shadow(
//                    elevation = 20.dp,
//                    ambientColor = Color(0x14000000),
//                    spotColor = Color(0x14000000)
//                )
//                .background(
//                    Theme.colors.surfaceColors.surfaceHigh
//                )
//                .padding(vertical = 12.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            ) {
//                Button(
//                    onClick = onSubmit,
//                    enabled = state.isFormValid,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp),
//                    shape = RoundedCornerShape(100.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if (state.isFormValid)
//                            Theme.colors.primary
//                        else
//                            Theme.colors.text.disable
//                    )
//                ) {
//                    Text(state.bottomText, style = Theme.textStyle.label.large)
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                TextButton(
//                    onClick = onCancel,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp)
//                        .border(
//                            width = 1.dp,
//                            color = Theme.colors.stroke,
//                            shape = RoundedCornerShape(100.dp)
//                        )
//                        .background(
//                            Color.White,
//                            shape = RoundedCornerShape(100.dp)
//                        )
//                ) {
//                    Text(
//                        "Cancel",
//                        style = Theme.textStyle.label.large,
//                        color = Theme.colors.primary
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CategoryFormScreenContentPreview() {
//    val mockState = CategoryFormState(
//        isVisible = true,
//        isEdit = true,
//        bottomText = "Add",
//        categoryName = "Work",
//        imageUri = null
//    )
//
//    Surface(color = Color.Black) {
//        CategoryBottomSheetContent(
//            state = mockState,
//            onCancel = {},
//            onSubmit = {},
//            onTitleChange = {},
//            onImageClick = {}
//        )
//    }
//}
//
//
//
//fun Modifier.dashedBorder(
//    strokeWidth: Dp,
//    color: Color,
//    cornerRadius: Dp = 0.dp,
//    dashLength: Dp = 10.dp,
//    gapLength: Dp = 10.dp
//): Modifier = this.then(
//    Modifier.drawWithCache {
//        val stroke = Stroke(
//            width = strokeWidth.toPx(),
//            pathEffect = PathEffect.dashPathEffect(
//                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
//            )
//        )
//        onDrawBehind {
//            drawRoundRect(
//                color = color,
//                size = size,
//                style = stroke,
//                cornerRadius = CornerRadius(cornerRadius.toPx())
//            )
//        }
//    }
//)