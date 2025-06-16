package com.example.tudeeapp.presentation.screen.onBoarding.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.onBoarding.Page

@Composable
fun BottomSection(
    page: Page,
    onNextClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 32.dp, top = 37.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Theme.colors.surfaceColors.onPrimaryColors.onPrimaryCard,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Theme.colors.surfaceColors.onPrimaryColors.onPrimaryStroke,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 48.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                Text(
                    text = page.title,
                    color = Theme.colors.text.title,
                    style = Theme.textStyle.title.medium,
                    modifier = Modifier.height(44.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = page.description,
                    color = Theme.colors.text.body,
                    style = Theme.textStyle.body.medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        TudeeButton(
            onClick = onNextClick,
            variant = ButtonVariant.FloatingActionButton,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_double),
                    contentDescription = "Next Page",
                    modifier = Modifier.size(28.dp)
                )
            },
            modifier = Modifier
                .size(64.dp)
                .offset(y = (-28).dp)
        )
    }
}