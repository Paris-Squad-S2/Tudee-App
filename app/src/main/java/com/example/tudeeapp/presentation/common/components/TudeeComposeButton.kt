package com.example.tudeeapp.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme

enum class ButtonState {
    Normal,
    Disabled,
    Loading,
    Error
}

sealed class ButtonVariant {
    object Filled : ButtonVariant()
    object Outlined : ButtonVariant()
    object TextOnly : ButtonVariant()
    object FAB : ButtonVariant()
}

@Composable
fun TudeeComposeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.Normal,
    variant: ButtonVariant = ButtonVariant.Filled,
    shape: Shape = RoundedCornerShape(100.dp),
    contentColor: Color = when (variant) {
        ButtonVariant.Outlined, ButtonVariant.TextOnly -> Theme.colors.primary
        else -> Theme.colors.surfaceColors.onPrimaryColors.onPrimary
    },
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
    loadingIndicator: @Composable (() -> Unit) = {
        RotatingIconLoadingIndicator(
            modifier = Modifier.size(20.dp),
        )
    },
    enabled: Boolean = state == ButtonState.Normal,
    textStyle: TextStyle = Theme.textStyle.label.large,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = icon,
    spacing: Dp = 8.dp,
    contentPadding: PaddingValues = when (variant) {
        ButtonVariant.TextOnly -> PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ButtonVariant.FAB -> PaddingValues(horizontal = 18.dp, vertical = 18.dp)
        else -> PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    },
    elevation: Dp = when (variant) {
        ButtonVariant.FAB -> 6.dp
        else -> 0.dp
    },
    backgroundBrush: Brush? = null

) {
    val interactionSource = remember { MutableInteractionSource() }

    val isLoading = state == ButtonState.Loading
    val isError = state == ButtonState.Error
    val isDisabled = state == ButtonState.Disabled

    val backgroundColorBrush = when (variant) {
        ButtonVariant.Filled, ButtonVariant.FAB -> when {
            isDisabled -> Brush.verticalGradient(
                colors = listOf(
                    Theme.colors.surfaceColors.disable,
                    Theme.colors.surfaceColors.disable
                )
            )

            isError -> Brush.verticalGradient(
                colors = listOf(
                    Theme.colors.status.errorVariant,
                    Theme.colors.status.errorVariant
                )
            )

            else -> backgroundBrush ?: Brush.verticalGradient(
                colors = Theme.colors.primaryGradient.colors,
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            )
        }

        else -> backgroundBrush ?: Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent
            )
        )
    }

    val borderColor = when (variant) {
        ButtonVariant.Outlined -> if (!isError) Theme.colors.text.stroke else Theme.colors.status.error.copy(
            alpha = 0.12f
        )

        else -> Color.Transparent
    }

    val textColor = when {
        isDisabled -> Theme.colors.text.stroke
        isError -> Theme.colors.status.error
        else -> contentColor
    }

    val emptyOnClick: () -> Unit = {}

    Surface(
        modifier = if (variant == ButtonVariant.FAB) {
            modifier
                .defaultMinSize(minWidth = 64.dp, minHeight = 40.dp)
//                .padding(6.dp)
                .shadow(elevation = elevation, shape = shape)

        } else {
            modifier.defaultMinSize(minWidth = 64.dp, minHeight = 40.dp)
        },
        shape = shape,
        color = Color.Transparent,
        contentColor = textColor,
        border = if (variant == ButtonVariant.Outlined) BorderStroke(1.dp, borderColor) else null,
        onClick = if (enabled && !isLoading) onClick else emptyOnClick,
        enabled = enabled && !isLoading,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentColor provides textColor) {
            ProvideTextStyle(textStyle) {
                Row(
                    modifier = Modifier
                        .background(brush = backgroundColorBrush)
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                        if (text != null) Spacer(modifier = Modifier.width(spacing))
                    }

                    text?.let {
                        Text(text = it)
                    }

                    if (isLoading) {
                        if (text != null) Spacer(modifier = Modifier.width(spacing))
                        AnimatedVisibility(visible = true, enter = fadeIn()) {
                            loadingIndicator()
                        }
                    } else {
                        trailingIcon?.let {
                            if (text != null) Spacer(modifier = Modifier.width(spacing))
                            it()
                        }
                    }

                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TextOnlyButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { }, text = "Submit"
            )
        }
    }
}

@Composable
@PreviewLightDark
fun IconOnlyButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                icon = { Icon(Icons.Default.Add, null) }
            )
        }
    }
}

@Composable
@PreviewLightDark
fun FABButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                icon = { Icon(Icons.Default.Add, null) },
                variant = ButtonVariant.FAB
            )
        }
    }
}

@Composable
@PreviewLightDark
fun OutlinedButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                variant = ButtonVariant.Outlined,
                text = "Cancel"
            )
        }
    }
}

@Composable
@PreviewLightDark
fun TextButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                variant = ButtonVariant.TextOnly,
                text = "Link"
            )
        }
    }
}

@Composable
@PreviewLightDark
fun ErrorStateButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                text = "Retry",
                state = ButtonState.Error
            )
        }
    }
}


@Composable
@PreviewLightDark
fun DisabledStateButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                text = "Submit",
                state = ButtonState.Disabled
            )
        }
    }
}


@Composable
@PreviewLightDark
fun IconTextButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                text = "Upload",
                icon = { Icon(Icons.Default.Add, null) },
            )
        }
    }
}

@Composable
@PreviewLightDark
fun LoadingStateButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeComposeButton(
                onClick = { },
                text = "Processing",
                state = ButtonState.Loading
            )
        }
    }
}


