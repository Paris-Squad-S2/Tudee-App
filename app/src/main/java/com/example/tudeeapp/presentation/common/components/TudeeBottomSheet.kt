@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeBottomSheet(
    modifier: Modifier = Modifier,
    stopBarrierDismiss: Boolean = false,
    showSheet: Boolean,
    title: String,
    initialHeight: Dp = 350.dp,
    onDismiss: () -> Unit,
    optionalActionButton: @Composable () -> Unit = {},
    stickyFooterContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {


    val bottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { newValue ->
                if (stopBarrierDismiss) {
                    newValue != SheetValue.Hidden
                } else {
                    true
                }
            }
        )

    LaunchedEffect(showSheet) {
        if (showSheet) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }
    val coroutineScope = rememberCoroutineScope()
    val allowedHeights = listOf(280.dp,350.dp, 500.dp, 700.dp, 1000.dp)

    var currentHeight by remember { mutableStateOf(initialHeight) }

    fun findClosestHeight(target: Dp): Dp {
        if (target < (allowedHeights.minOrNull() ?: return 100.dp)) { return 100.dp }
        return allowedHeights.minByOrNull { kotlin.math.abs(it.value - target.value) } ?: allowedHeights.first()
    }

    if (!showSheet) return
    ModalBottomSheet(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .statusBarsPadding(),
        containerColor = Theme.colors.surfaceColors.surface,
        onDismissRequest = {
            onDismiss()
        },
        dragHandle = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                val newHeight = currentHeight - dragAmount.toDp()
                                currentHeight = (newHeight).coerceAtLeast(0.dp)
                            },
                            onDragEnd = {
                                val snapped = findClosestHeight(currentHeight)
                                currentHeight = snapped
                                if (currentHeight < (allowedHeights.minOrNull() ?: 100.dp)) {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                        onDismiss()
                                    }
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                BottomSheetDefaults.DragHandle(width = 32.dp)
            }
        },
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Column(
            Modifier.height(currentHeight)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .then(Modifier.verticalScroll(rememberScrollState()))
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
                    optionalActionButton()
                    Spacer(modifier = Modifier.width(16.dp))
                }
                content()
            }
            stickyFooterContent()
        }
    }
}


@PreviewLightDark
@Composable
private fun TudeeBottomSheetPreview() {
    BasePreview {
        var isSheetOpen by remember { mutableStateOf(true) }
        TudeeBottomSheet(
            showSheet = isSheetOpen,
            title = "Sample Title",
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
            stickyFooterContent = {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = { isSheetOpen = false },
                ) { Text(text = "Close") }
            }
        )
    }
}