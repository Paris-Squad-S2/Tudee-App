package com.example.tudeeapp.presentation.screen.onBoarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.tudeeapp.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.onBoarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
    isLastPage: Boolean,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.status.overlay)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!isLastPage) {
                TextButton(
                    modifier = Modifier.align(Alignment.Start),
                    onClick = onSkipClick
                ) {
                    Text(
                        text = "Skip",
                        color = Theme.colors.primary,
                        style = Theme.textStyle.label.large
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = page.image),
                    contentDescription = "Onboarding robot",
                    contentScale = ContentScale.FillBounds
                )
                BottomSection(
                    page = page,
                    onNextClick = onNextClick,
                )
            }
        }
    }
}

@Preview(
    name = "Galaxy J5 Prime",
    device = "spec:width=360dp,height=640dp,dpi=320"
)
@Composable
private fun onBoardingPagePreview() {
    BasePreview {
        val page = Page(
            image = R.drawable.img_robot5,
            title = stringResource(id = R.string.onboarding3title),
            description = stringResource(id = R.string.onboarding3description)
        )
        OnBoardingPage(
            page = page,
            isLastPage = true,
            onNextClick = {},
            onSkipClick = {}
        )
    }
}