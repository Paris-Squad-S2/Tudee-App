@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun TudeeBottomSheet(
    isVisible: Boolean,
    title: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    headerEnd: @Composable () -> Unit = {},
    isScrollable: Boolean = true,
    skipPartiallyExpanded: Boolean = true,
    stickyBottomContent: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollModifier =
        if (isScrollable) Modifier.verticalScroll(rememberScrollState()) else Modifier
    val bottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmValueChange = { newValue ->
                false
            }
        )

    LaunchedEffect(isVisible) {
        when {
            isVisible -> bottomSheetState.show()
            else -> bottomSheetState.hide()
        }
    }
    val coroutineScope = rememberCoroutineScope()
    var currentHeight by remember { mutableStateOf(300.dp) } // Start partially opened

    if (!isVisible) return
    BoxWithConstraints {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxWidth(),
            containerColor = Theme.colors.surfaceColors.surface,
            onDismissRequest = { },
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                currentHeight =
                                    (currentHeight - dragAmount.toDp())
                                if (currentHeight <= 100.dp) {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                        onDismiss()
                                    }
                                }
                            }
                        }
                        .padding(horizontal = ((this.maxWidth - 32.dp)/2).coerceAtLeast(0.dp))
                )
            },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .height(currentHeight)
                        .then(scrollModifier)
                ) {
                    Row {
                        Text(
                            text = title,
                            style = Theme.textStyle.title.large,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        headerEnd()
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    content()
                }
                stickyBottomContent()
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun TudeeBottomSheetPreview() {
    BasePreview {
        var isSheetOpen by remember { mutableStateOf(true) }
        TudeeBottomSheet(
            isVisible = isSheetOpen,
            title = "Sample Title",
            isScrollable = true,
            skipPartiallyExpanded = true,
            onDismiss = { isSheetOpen = false },
            content = {
                Column {
                    repeat(40) {
                        Text(
                            text = "Sample content inside the bottom sheet.",
                            style = Theme.textStyle.body.medium,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                }
            },
            stickyBottomContent = {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = { isSheetOpen = false },
                ) { Text(text = "Close") }
            }
        )
    }
}