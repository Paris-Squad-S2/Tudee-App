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
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.onBoarding.Page


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.status.overlay)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_vectors),
            contentDescription = "Background Pattern",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(onClick = onSkipClick){
                    Text(
                        text = "Skip",
                        color = Theme.colors.primary,
                        style = Theme.textStyle.label.large
                    )
                }
            }
            Spacer(modifier = Modifier.height(188.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            vertical = 5.dp,
                            horizontal = 32.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = page.image),
                        contentDescription = "Onboarding robot",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            BottomSection(
                page = page,
                onNextClick = onNextClick
            )
        }
    }
}
