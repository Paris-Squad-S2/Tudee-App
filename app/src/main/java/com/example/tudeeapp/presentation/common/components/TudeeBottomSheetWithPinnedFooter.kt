@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import kotlinx.coroutines.delay
import kotlin.math.abs

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TudeeBottomSheetWithPinnedFooter(
    modifier: Modifier  = Modifier,
    showBottomSheet: Boolean,
    title: String,
    onDismiss: () -> Unit,
    headerEnd: @Composable () -> Unit = {},
    isScrollable: Boolean = true,
    isDraggable: Boolean = false,
    skipPartiallyExpanded: Boolean = true,
    stickyBottomContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollModifier =
        if (isScrollable) Modifier.verticalScroll(rememberScrollState()) else Modifier
    val bottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmValueChange = { newValue ->
                if (isDraggable) {
                    newValue != SheetValue.Hidden
                } else {
                    true
                }
            })

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val footerHeightPx = remember { mutableFloatStateOf(0f) }
    var footerOffset by remember { mutableFloatStateOf(0f) }

    val screenHeightPx = remember(density, configuration) {
        with(density) { configuration.screenHeightDp.dp.toPx() }
    }

    val sheetOffset = remember { derivedStateOf { bottomSheetState.requireOffset() } }

    val windowInsets = WindowInsets.navigationBars
    val imeInsets = WindowInsets.ime

    val isImeVisible = WindowInsets.isImeVisible

    // Calculate inset heights more efficiently
    val bottomInsetHeight = remember(isImeVisible, density) {
        derivedStateOf {
            if (isImeVisible) imeInsets.getBottom(density)
            else windowInsets.getBottom(density)
        }
    }

    LaunchedEffect(showBottomSheet) {
        delay(100)
        when {
            showBottomSheet -> bottomSheetState.show()
            else -> bottomSheetState.hide()
        }
    }

    ModalBottomSheet(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp),
        containerColor = Theme.colors.surfaceColors.surface,
        onDismissRequest = { onDismiss() },
        dragHandle = { BottomSheetDefaults.DragHandle() },
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Box(modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .heightIn(
                        max = with(density) { footerOffset.toDp() }
                    )
            ) {
                Column(scrollModifier) {
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
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        if (abs(footerHeightPx.floatValue - size.height.toFloat()) > 1f) {
                            footerHeightPx.floatValue = size.height.toFloat()
                        }
                    }
                    .imePadding()
                    .graphicsLayer {
                        // Calculate translation with derivedState values
                        val calculatedTranslation =
                            (screenHeightPx - sheetOffset.value - size.height - bottomInsetHeight.value)
                                .coerceAtLeast(0f)
                        footerOffset = calculatedTranslation
                        translationY = calculatedTranslation
                    },

                ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        stickyBottomContent()
                    }
                }
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
            showBottomSheet = isSheetOpen,
            title = "Sample Title",
            isScrollable = true,
            skipPartiallyExpanded = true,
            onDismiss = { isSheetOpen = true },
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
