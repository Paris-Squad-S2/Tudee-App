package com.example.tudeeapp.presentation.common.extentions

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color(0xFFBFD2FF),
    blur: Dp = 4.dp,
    offsetY: Dp = 2.dp,
    offsetX: Dp = 2.dp,
    spread: Dp = 0.dp,
) = this.drawWithContent {
    drawContent()
    drawIntoCanvas { canvas ->
        val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)
        // Initialize a Paint object
        val paint = Paint()
        paint.color = color

// Save the current layer of the canvas
        canvas.saveLayer(size.toRect(), paint)
// Draw the outline of the shadow onto the canvas
        canvas.drawOutline(shadowOutline, paint)

// Configure the paint to act as an eraser to clip the shadow
        paint.asFrameworkPaint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (blur.toPx() > 0) {
                maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
        }

// Set clipping color
        paint.color = Color(0xFFBFD2FF)

// Translate the canvas to the offset position
        canvas.translate(offsetX.toPx(), offsetY.toPx())
// Draw the outline again to clip the shadow
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}
