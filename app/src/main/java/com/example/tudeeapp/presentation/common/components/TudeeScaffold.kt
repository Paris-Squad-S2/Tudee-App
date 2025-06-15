package com.example.tudeeapp.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme
import kotlinx.coroutines.delay


@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {
        TudeeButton(
            modifier = Modifier.size(64.dp),
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_note_add),
                    contentDescription = null
                )
            },
            variant = ButtonVariant.FloatingActionButton
        )
    },
    backgroundColor: Color = Theme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    onToggleTheme: () -> Unit = {},
    bottomBar: @Composable () -> Unit = { TudeeNavigationBar() },
    isDarkMode: Boolean = false,
    showTopBar: Boolean = false,
    showFloatingActionButton: Boolean = false,
    showBottomBar: Boolean = false,
    content: @Composable (snackBar: SnackBarState) -> Unit,
    contentBackground: Color = Theme.colors.surfaceColors.surface,
) {
    val snackBarState = remember { SnackBarState() }

    Box {
        Scaffold(
            modifier = modifier
                .background(backgroundColor)
                .statusBarsPadding(),
            topBar =
                {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (showTopBar) {
                            Header(
                                isDarkMode = isDarkMode,
                                onToggleTheme = onToggleTheme,
                            )
                        }
                    }
                },
            bottomBar = if (showBottomBar) {
                bottomBar
            } else {
                { }
            },
            floatingActionButton = if (showFloatingActionButton)
                floatingActionButton else {
                { }
            },
            contentColor = contentColor,
            containerColor = backgroundColor,
            contentWindowInsets = WindowInsets(0.dp)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(contentBackground)
                    .padding(innerPadding)
            ) {
                content(snackBarState)
            }
        }
        if (snackBarState.isVisible == true) {
            AnimatedVisibility(
                visible = snackBarState.isVisible,
                enter = fadeIn(animationSpec = tween(snackBarState.durationMillis)),
                exit = fadeOut(animationSpec = tween(snackBarState.durationMillis))
            ) {
                SnackBar(
                    modifier
                        .statusBarsPadding()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    text = snackBarState.message,
                    isSuccess = snackBarState.isSuccess,
                    onClick = { snackBarState.hide() }
                )
            }
            LaunchedEffect(Unit) {
                delay(snackBarState.durationMillis.toLong())
                snackBarState.hide()
            }
        }
    }
}


@Composable
@Preview
private fun TudeeScaffoldPreview() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface) {
            TudeeScaffold(
                showTopBar = true,
                showFloatingActionButton = true,
                showBottomBar = true,
                content = { snakeBar ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                snakeBar.show("Hello World")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello World")
                    }
                },
            )
        }
    }
}
