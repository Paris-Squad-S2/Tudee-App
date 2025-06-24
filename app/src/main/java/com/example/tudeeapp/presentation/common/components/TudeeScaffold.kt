package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme
import com.example.tudeeapp.presentation.navigation.NavigatorImpl
import com.example.tudeeapp.presentation.navigation.Destinations

@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    backgroundColor: Color = Theme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    contentBackground: Color = Theme.colors.surfaceColors.surface,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { topBar() },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        contentColor = contentColor,
        containerColor = backgroundColor,
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(contentBackground)
                .padding(innerPadding)
        ) {
            content()
        }
    }
}

@Composable
@PreviewLightDark
private fun TudeeScaffoldPreview() {
    var toggled by remember { mutableStateOf(false) }

    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface) {
            TudeeScaffold(
                floatingActionButton = {
                    TudeeButton(
                        modifier = Modifier.size(64.dp),
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_note_add),
                                contentDescription = stringResource(R.string.add_task)
                            )
                        },
                        variant = ButtonVariant.FloatingActionButton
                    )
                },
                bottomBar = {
                    TudeeNavigationBar(
                        rememberNavController(),
                        navigator = NavigatorImpl(startGraph = Destinations.TudeeGraph),
                    )
                },
                topBar = {
                    AppHeader(
                        modifier = Modifier
                            .background(Theme.colors.primary)
                            .statusBarsPadding(),
                        isDarkMode = toggled,
                        onToggleTheme = { toggled = it },
                    )
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello World")
                    }
                },
            )
        }
    }
}