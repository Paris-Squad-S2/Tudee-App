package com.example.tudeeapp.presentation.screen.onBoarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize:Int,
    currentPage: Int,
    selectedColor: Color = Theme.colors.primary,
    unSelectedColor: Color = Theme.colors.primaryVariant
)
{
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pageSize) {
            Spacer(Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(5.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(
                        color = if(it==currentPage) selectedColor else unSelectedColor
                    )
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PageIndicatorPreview()
{
    PageIndicator(
        pageSize = 3,
        currentPage = 1
    )
}