package com.example.tudeeapp.presentation.screen.onBoarding.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.onBoarding.Page


@Composable
fun BottomSection(
    page: Page,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(top = 32.dp, bottom = 60.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Theme.colors.surfaceColors.onPrimaryColors.onPrimaryCard,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .border(
                    width = 1.dp,
                    color = Theme.colors.surfaceColors.onPrimaryColors.onPrimaryStroke,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 48.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
            ) {
                Text(
                    text = page.title,
                    color = Theme.colors.text.title,
                    style = Theme.textStyle.title.medium,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = page.description,
                    color = Theme.colors.text.body,
                    style = Theme.textStyle.body.small,
                    textAlign = TextAlign.Center,
                )
            }
        }

        TudeeButton(
            onClick = onNextClick,
            variant = ButtonVariant.FloatingActionButton,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(64.dp)
                .offset(y = (28).dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right_double),
                    contentDescription = "Next Page",
                )
            }
        )
    }
}

@Preview
@Composable
private fun BottomSheetPreview() {
    val page = Page(
        image = R.drawable.img_robot4,
        title = stringResource(id = R.string.onboarding1title),
        description = stringResource(id = R.string.onboarding1description)
    )
    BasePreview {
        BottomSection(
            page = page,
            onNextClick = {}
        )
    }
}