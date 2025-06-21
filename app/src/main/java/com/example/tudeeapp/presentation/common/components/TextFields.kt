package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Int?,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
            }
        }
    }

    val borderColor = when {
        isFocused -> Theme.colors.primary
        else -> Theme.colors.stroke
    }

    val textColor = if (isFocused) {
        Theme.colors.text.body
    } else {
        Theme.colors.text.hint
    }

    val textStyle = if (isFocused) {
        Theme.textStyle.label.medium
    } else {
        Theme.textStyle.body.medium
    }

    val iconColor = if (isFocused) Theme.colors.text.body else Theme.colors.text.hint
    val borderWidth = 1.dp
    val shape = RoundedCornerShape(16.dp)
    val separatorColor = Theme.colors.surfaceColors.surfaceLow

    BasicTextField(
        value = value,
        enabled = enabled,
        onValueChange = { newText ->
            if (singleLine){
                if (newText.length<=100){
                    onValueChange(newText)
                }
            }else{
                if (newText.lines().size <= 11) { onValueChange(newText) }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(if (singleLine) 56.dp else 168.dp)
            .background(Theme.colors.surfaceColors.surfaceHigh, shape)
            .border(BorderStroke(borderWidth, borderColor), shape)
            .clip(shape)
            .clickable { onClick() },
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else 11,
        textStyle = textStyle,
        cursorBrush = SolidColor(Theme.colors.primary),
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    HandleLeadingIcon(leadingIcon, iconColor, singleLine, separatorColor)
                }

                HandleInputText(
                    singleLine,
                    value,
                    placeholder,
                    textColor,
                    textStyle,
                    innerTextField
                )

            }
        }
    )
}


@Composable
private fun HandleLeadingIcon(
    leadingIcon: Int?,
    iconColor: Color,
    singleLine: Boolean,
    separatorColor: Color,
) {
    if (leadingIcon != null) {
        Image(
            painter = painterResource(id = leadingIcon),
            contentDescription = "Leading Icon",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier.size(24.dp)
        )
    }

    if (singleLine) {
        Spacer(Modifier.width(8.dp))
        VerticalDivider(
            color = separatorColor,
            modifier = Modifier
                .height(24.dp),
            thickness = 1.dp
        )
        Spacer(Modifier.width(16.dp))
    } else {
        Spacer(Modifier.width(8.dp))
    }
}

@Composable
private fun RowScope.HandleInputText(
    singleLine: Boolean,
    value: String,
    placeholder: String,
    textColor: Color,
    textStyle: TextStyle,
    innerTextField: @Composable () -> Unit,
) {
    val verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
    val topPadding = if (singleLine) 0.dp else 12.dp

    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(top = topPadding),
        verticalAlignment = verticalAlignment
    ) {
        InputFieldContent(
            value = value,
            placeholder = placeholder,
            textColor = textColor,
            innerTextField = innerTextField,
            textStyle = textStyle
        )
    }
}

@Composable
private fun InputFieldContent(
    value: String,
    placeholder: String,
    textColor: Color,
    textStyle: TextStyle,
    innerTextField: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = textColor,
                fontSize = 14.sp,
                style = textStyle
            )
        }
        innerTextField()
    }
}

@PreviewMultiDevices
@Composable
fun PreviewTextField() {
    BasePreview {

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var defaultText by remember { mutableStateOf("") }
            TextField(
                value = defaultText,
                onValueChange = { defaultText = it },
                placeholder = "Full name",
                enabled = false,
                leadingIcon = R.drawable.ic_profile
            )


            var multiLineText by remember { mutableStateOf("") }
            TextField(
                value = multiLineText,
                onValueChange = { multiLineText = it },
                placeholder = "Description",
                singleLine = false,
                leadingIcon = null
            )
        }

    }
}