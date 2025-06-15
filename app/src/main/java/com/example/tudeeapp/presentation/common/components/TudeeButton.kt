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
    shape: Shape = RoundedCornerShape(100.dp),
    contentColor: Color = when (variant) {
        ButtonVariant.OutlinedButton, ButtonVariant.TextButton -> Theme.colors.primary
        else -> getContentColor(state)
    },
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = Theme.textStyle.label.large,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = icon,
    contentPadding: PaddingValues = getContentPadding(variant),
    elevation: Dp = when (variant) {
        ButtonVariant.FloatingActionButton -> 6.dp
        else -> 0.dp
    },
    backgroundBrush: Brush? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isLoading = (state == ButtonState.Loading)
    val enabled = (state == ButtonState.Normal || state == ButtonState.Error)
    val spacing = 8.dp
    val backgroundColorBrush = getBackgroundBrush(variant, state, backgroundBrush)
    val borderColor = getBorderColor(variant, state)

    Surface(
        modifier = modifier
            .defaultMinSize(minWidth = 64.dp, minHeight = 40.dp)
            .applyElevationIfFAB(variant == ButtonVariant.FloatingActionButton, elevation, shape),
        shape = shape,
        color = Color.Transparent,
        contentColor = contentColor,
        border = if (variant == ButtonVariant.OutlinedButton) BorderStroke(1.dp, borderColor) else null,
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            ProvideTextStyle(textStyle) {
                Row(
                    modifier = Modifier
                        .background(backgroundColorBrush)
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                        if (text != null) Spacer(modifier = Modifier.width(spacing))
                    }

                    text?.let { Text(it) }

                    if (isLoading) {
                        if (text != null) Spacer(modifier = Modifier.width(spacing))
                        AnimatedVisibility(visible = true, enter = fadeIn()) {
                            RotatingIconLoadingIndicator(modifier = Modifier.size(20.dp))
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
private fun getBackgroundBrush(
    variant: ButtonVariant,
    state: ButtonState,
    backgroundBrush: Brush?
): Brush {
    val isDisabled = state == ButtonState.Disabled
    val isError = state == ButtonState.Error

    return when (variant) {
        ButtonVariant.FilledButton, ButtonVariant.FloatingActionButton -> {
            if (isDisabled) {
                Brush.verticalGradient(
                    colors = listOf(
                        Theme.colors.surfaceColors.disable,
                        Theme.colors.surfaceColors.disable
                    )
                )
            } else if (isError) {
                Brush.verticalGradient(
                    colors = listOf(
                        Theme.colors.status.errorVariant,
                        Theme.colors.status.errorVariant
                    )
                )
            } else {
                backgroundBrush ?: Brush.verticalGradient(
                    colors = Theme.colors.primaryGradient.colors
                )
            }
        }

        else -> {
            backgroundBrush
                ?: Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Transparent)
                )
        }
    }
}


@Composable
private fun getBorderColor(variant: ButtonVariant, state: ButtonState): Color {
    val isError = state == ButtonState.Error
    return when (variant) {
        ButtonVariant.OutlinedButton ->
            if (!isError) Theme.colors.text.disable
            else Theme.colors.status.error.copy(alpha = 0.12f)

        else -> Color.Transparent
    }
}


@Composable
private fun getContentColor(state: ButtonState): Color {
    return when (state) {
        ButtonState.Disabled -> Theme.colors.text.disable
        ButtonState.Error -> Theme.colors.status.error
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

private fun Modifier.applyElevationIfFAB(isFAB: Boolean, elevation: Dp, shape: Shape): Modifier {
    return if (isFAB) this.shadow(elevation = elevation, shape = shape) else this
}


@Composable
@PreviewLightDark
fun TextOnlyButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeButton(
                onClick = { }, text = "Submit",
                variant = ButtonVariant.FilledButton,
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
            TudeeButton(
                onClick = { },
                icon = { Icon(Icons.Default.Add, null) },
                variant = ButtonVariant.FilledButton,

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
            TudeeButton(
                onClick = { },
                icon = { Icon(Icons.Default.Add, null) },
                variant = ButtonVariant.FloatingActionButton
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
            TudeeButton(
                onClick = { },
                variant = ButtonVariant.OutlinedButton,
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
            TudeeButton(
                onClick = { },
                variant = ButtonVariant.TextButton,
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
            TudeeButton(
                onClick = { },
                text = "Retry",
                state = ButtonState.Error,
                variant = ButtonVariant.FilledButton,
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
            TudeeButton(
                onClick = { },
                text = "Submit",
                state = ButtonState.Disabled,
                variant = ButtonVariant.FilledButton,
            )
        }
    }
}


@Composable
@PreviewLightDark
fun TrailingTextButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeButton(
                onClick = { },
                text = "Upload",
                trailingIcon = { Icon(Icons.Default.Add, null) },
                variant = ButtonVariant.FilledButton,
            )
        }
    }
}

@Composable
@PreviewLightDark
fun LeadingTextButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surface)
        {
            TudeeButton(
                onClick = { },
                text = "Upload",
                leadingIcon = { Icon(Icons.Default.Add, null) }, variant = ButtonVariant.FilledButton,
            )
        }
    }
}

@Composable
@PreviewLightDark
fun LoadingStateButton() {
    TudeeTheme {
        Surface(color = Theme.colors.surfaceColors.surfaceHigh)
        {
            TudeeButton(
                onClick = { },
                text = "Processing",
                state = ButtonState.Loading,
                variant = ButtonVariant.FilledButton,
            )
        }
    }
}


