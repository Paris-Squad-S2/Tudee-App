package com.example.tudeeapp.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme

enum class ButtonState {
    Normal,
    Disabled,
    Loading
}

sealed class ButtonVariant {
    object FilledButton : ButtonVariant()
    object OutlinedButton : ButtonVariant()
    object TextButton : ButtonVariant()
    object FloatingActionButton : ButtonVariant()
}

@Composable
fun TudeeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.Normal,
    variant: ButtonVariant,
    isNegative: Boolean = false,
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isLoading = (state == ButtonState.Loading)
    val enabled = (state == ButtonState.Normal)
    val spacing = 8.dp
    val backgroundColorBrush = getBackgroundBrush(
        variant,
        isDisabled = (state == ButtonState.Disabled),
        isNegative = isNegative,
    )
    val borderColor = getBorderColor(variant, isNegative)
    val buttonContentColor = getContentColor(state, variant, isNegative)
    val buttonElevation = when (variant) {
        ButtonVariant.FloatingActionButton -> 6.dp
        else -> 0.dp
    }

    Surface(
        modifier = modifier
            .shadow(elevation = buttonElevation, shape = RoundedCornerShape(100.dp)),
        shape = RoundedCornerShape(100.dp),
        color = Color.Transparent,
        contentColor = buttonContentColor,
        border = if (variant == ButtonVariant.OutlinedButton) BorderStroke(
            1.dp,
            borderColor
        ) else null,
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Row(
            modifier = Modifier
                .background(backgroundColorBrush)
                .padding(getContentPadding(variant)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            text?.let { Text(it, style = Theme.textStyle.label.large, color = buttonContentColor) }

            if (isLoading) {
                if (text != null) Spacer(modifier = Modifier.width(spacing))
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    RotatingIconLoadingIndicator(
                        modifier = Modifier.size(20.dp),
                        color = buttonContentColor
                    )
                }
            } else {
                icon?.let {
                    if (text != null) Spacer(modifier = Modifier.width(spacing))
                    it()
                }
            }
        }
    }
}

@Composable
private fun getBackgroundBrush(
    variant: ButtonVariant,
    isDisabled: Boolean,
    isNegative: Boolean
): Brush {
    return when (variant) {
        ButtonVariant.FilledButton, ButtonVariant.FloatingActionButton -> {
            if (isDisabled) {
                Brush.verticalGradient(
                    colors = listOf(
                        Theme.colors.surfaceColors.disable,
                        Theme.colors.surfaceColors.disable
                    )
                )
            } else if (isNegative) {
                Brush.verticalGradient(
                    colors = listOf(
                        Theme.colors.status.errorVariant,
                        Theme.colors.status.errorVariant
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = Theme.colors.primaryGradient.colors
                )
            }
        }

        else -> {
            Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Transparent)
            )
        }
    }
}


@Composable
private fun getBorderColor(variant: ButtonVariant, isNegative: Boolean): Color {
    return when (variant) {
        ButtonVariant.OutlinedButton ->
            if (!isNegative) Theme.colors.stroke
            else Theme.colors.status.error.copy(alpha = 0.12f)

        else -> Color.Transparent
    }
}


@Composable
private fun getContentColor(
    state: ButtonState,
    variant: ButtonVariant,
    isNegative: Boolean
): Color {
    return when {
        state == ButtonState.Disabled -> Theme.colors.text.disable
        isNegative -> Theme.colors.status.error
        (variant == ButtonVariant.OutlinedButton || variant == ButtonVariant.TextButton) -> Theme.colors.primary
        else -> Theme.colors.surfaceColors.onPrimaryColors.onPrimary
    }
}

@Composable
private fun getContentPadding(variant: ButtonVariant): PaddingValues {
    return when (variant) {
        ButtonVariant.TextButton -> PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ButtonVariant.FloatingActionButton -> PaddingValues(horizontal = 18.dp, vertical = 18.dp)
        else -> PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    }
}


@Composable
@PreviewLightDark
fun PrimaryButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.FilledButton,
                )
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.FilledButton,
                    state = ButtonState.Disabled
                )
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.FilledButton,
                    state = ButtonState.Loading
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun FABButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TudeeButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                    variant = ButtonVariant.FloatingActionButton,
                )
                TudeeButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                    variant = ButtonVariant.FloatingActionButton,
                    state = ButtonState.Disabled
                )
                TudeeButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { },
                    icon = { Icon(Icons.Default.Add, null) },
                    variant = ButtonVariant.FloatingActionButton,
                    state = ButtonState.Loading
                )
            }
        }
    }
}


@Composable
@PreviewLightDark
fun TextButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TudeeButton(
                    onClick = { }, text = "Cancel",
                    variant = ButtonVariant.TextButton,
                )
                TudeeButton(
                    onClick = { }, text = "Cancel",
                    variant = ButtonVariant.TextButton,
                    state = ButtonState.Disabled
                )
                TudeeButton(
                    onClick = { }, text = "Cancel",
                    variant = ButtonVariant.TextButton,
                    state = ButtonState.Loading
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun OutlinedButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.OutlinedButton,
                )
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.OutlinedButton,
                    state = ButtonState.Loading
                )
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.OutlinedButton,
                    state = ButtonState.Disabled
                )
            }
        }
    }
}


@Composable
@PreviewLightDark
fun PrimaryNegativeButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.FilledButton,
                    isNegative = true
                )
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.FilledButton,
                    isNegative = true,
                    state = ButtonState.Disabled
                )
                TudeeButton(
                    onClick = { }, text = "Submit",
                    variant = ButtonVariant.FilledButton,
                    isNegative = true,
                    state = ButtonState.Loading
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TextNegativeButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TudeeButton(
                    onClick = { }, text = "Cancel",
                    variant = ButtonVariant.TextButton,
                    isNegative = true
                )
                TudeeButton(
                    onClick = { }, text = "Cancel",
                    variant = ButtonVariant.TextButton,
                    isNegative = true,
                    state = ButtonState.Disabled
                )
                TudeeButton(
                    onClick = { }, text = "Cancel",
                    variant = ButtonVariant.TextButton,
                    isNegative = true,
                    state = ButtonState.Loading
                )
            }
        }
    }
}