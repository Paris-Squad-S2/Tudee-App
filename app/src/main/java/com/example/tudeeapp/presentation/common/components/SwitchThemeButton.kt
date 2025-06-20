package com.example.tudeeapp.presentation.common.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.innerShadow
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun SwitchThemeButton(
    toggled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val iconRes = if (toggled) R.drawable.ic_ellipse_night else R.drawable.ic_ellipse_day

    val offsetX by animateDpAsState(
        targetValue = if (toggled) 28.dp else 0.dp, animationSpec = tween(
            durationMillis = 1000,
        )
    )

    AnimatedContent(
        targetState = iconRes, transitionSpec = {
            fadeIn(animationSpec = tween(1000)) togetherWith fadeOut(animationSpec = tween(1000))
        },
        modifier = modifier
    ) { icon ->
        Box(
            modifier = Modifier
                .size(width = 64.dp, height = 36.dp)
                .border(
                    width = 1.dp, color = Theme.colors.stroke, shape = RoundedCornerShape(100)
                )
                .clip(shape = RoundedCornerShape(100))
                .background(
                    color = if (toggled) Color(0xFF151535) else Theme.colors.primary
                )
                .clickable {
                    onToggle(!toggled)
                }
                .padding(2.dp),

            ) {

            AnimatedVisibility(visible = toggled) {
                Icon(
                    painter = painterResource(R.drawable.ic_moon_icon_bg), contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(29.dp)
                        .offset(x = 2.dp, y = 2.dp)
                )
            }

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.offset(x = offsetX)
            )
            Ellipse12CircleDay(toggled)
            Ellipse9CircleDay(toggled)
            MorphingCircle1(toggled)

            AnimatedVisibility(
                visible = toggled,
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .offset(x = 34.5.dp, y = 14.dp)
                        .background(color = Color(0xFFE9EFFF), CircleShape)
                        .innerShadow(
                            shape = CircleShape,
                            blur = 4.dp,
                            offsetY = 1.dp,
                            offsetX = 1.dp,
                        )
                ) {}

            }
            Ellipse5CircleDay(toggled)
            MorphingCircle2(toggled)
            Ellipse14CircleDay(toggled)
        }
    }
}


@Composable
private fun MorphingCircle1(toggled: Boolean, modifier: Modifier = Modifier) {
    val size by animateDpAsState(
        targetValue = if (toggled) 8.dp else 29.dp,
        animationSpec = tween(1500)
    )

    val offsetX by animateDpAsState(
        targetValue = if (toggled) 39.dp else 45.dp,
        animationSpec = tween(1500)
    )

    val offsetY by animateDpAsState(
        targetValue = if (toggled) 4.dp else 0.dp,
        animationSpec = tween(1500)
    )

    val color by animateColorAsState(
        targetValue = if (toggled) Color(0xFFE9EFFF) else Color.White,
        animationSpec = tween(1500)
    )

    Box(
        modifier = modifier
            .offset(x = offsetX, y = offsetY)
            .size(size)
            .background(color = color, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                blur = if (toggled) 4.dp else 0.dp,
                offsetY = 1.dp,
                offsetX = 1.dp,
            )
    )
}

@Composable
private fun MorphingCircle2(toggled: Boolean, modifier: Modifier = Modifier) {
    val size by animateDpAsState(
        targetValue = if (toggled) 4.dp else 29.dp,
        animationSpec = tween(1500)
    )

    val offsetX by animateDpAsState(
        targetValue = if (toggled) 49.dp else 45.dp,
        animationSpec = tween(1500)
    )

    val offsetY by animateDpAsState(
        targetValue = if (toggled) 22.dp else 0.dp,
        animationSpec = tween(1500)
    )

    val color by animateColorAsState(
        targetValue = if (toggled) Color(0xFFE9EFFF) else Color.White,
        animationSpec = tween(1500)
    )

    Box(
        modifier = modifier
            .offset(x = offsetX, y = offsetY)
            .size(size)
            .background(color = color, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                blur = if (toggled) 4.dp else 0.dp,
                offsetY = 1.dp,
                offsetX = 1.dp,
            )
    )
}

@Composable
private fun Ellipse5CircleDay(toggled: Boolean, modifier: Modifier = Modifier) {
    val offsetX by animateDpAsState(
        targetValue = if (toggled) 69.dp else 45.dp,
        animationSpec = tween(500)
    )

    val offsetY by animateDpAsState(
        targetValue = if (toggled) 50.dp else 24.dp,
        animationSpec = tween(500)
    )

    Box(
        modifier = modifier
            .offset(x = offsetX, y = offsetY)
            .size(16.dp)
            .background(color = Color.White, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                blur = if (toggled) 4.dp else 0.dp,
                offsetY = 0.dp,
                offsetX = 0.dp,
            )
    )
}

@Composable
private fun Ellipse14CircleDay(toggled: Boolean, modifier: Modifier = Modifier) {
    val offsetX by animateDpAsState(
        targetValue = if (toggled) (-20).dp else 35.5.dp,
        animationSpec = tween(1000)
    )


    Box(
        modifier = modifier
            .offset(x = offsetX, y = 24.dp)
            .size(height = 16.dp, width = 14.dp)
            .background(color = Color.White, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                blur = if (toggled) 4.dp else 0.dp,
                offsetY = 0.dp,
                offsetX = 0.dp,
            )
    )

}

@Composable
private fun Ellipse12CircleDay(toggled: Boolean, modifier: Modifier = Modifier) {


    val offsetX by animateDpAsState(
        targetValue = if (toggled) 100.dp else 40.dp,
        animationSpec = tween(1000)
    )

    Box(
        modifier = modifier
            .offset(x = offsetX, y = (-3).dp)
            .size(32.dp)
            .background(color = Color.White, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                blur = if (toggled) 4.dp else 0.dp,
                offsetY = 0.dp,
                offsetX = 0.dp,
            )
    )
}

@Composable
private fun Ellipse9CircleDay(toggled: Boolean, modifier: Modifier = Modifier) {

    val offsetX by animateDpAsState(
        targetValue = if (toggled) 39.dp else 32.dp,
        animationSpec = tween(1500)
    )

    val offsetY by animateDpAsState(
        targetValue = if (toggled) 34.dp else 20.dp,
        animationSpec = tween(100)
    )

    Box(
        modifier = modifier
            .offset(x = offsetX, y = offsetY)
            .size(24.dp)
            .background(color = Theme.colors.surfaceColors.surfaceLow, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                blur = if (toggled) 4.dp else 0.dp,
                offsetY = 0.dp,
                offsetX = 0.dp,
            )
    )
}

@Preview(showBackground = true)
@Composable
fun SwitchThemeButtonPreview() {
    var toggled by remember { mutableStateOf(false) }
    SwitchThemeButton(
        toggled = toggled,
        onToggle = { toggled = it }
    )
}