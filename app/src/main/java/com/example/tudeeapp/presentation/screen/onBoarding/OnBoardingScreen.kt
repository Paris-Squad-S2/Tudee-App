package com.example.tudeeapp.presentation.screen.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.navigation.LocalNavController
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import com.example.tudeeapp.presentation.screen.onBoarding.components.OnboardingImage
import com.example.tudeeapp.presentation.screen.onBoarding.components.PageIndicator
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.onBoarding.components.BottomSection


@Composable
fun OnboardScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    pages: List<Page>,
) {
    val navController = LocalNavController.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            navController.navigate(Screens.Home){
                popUpTo(Screens.OnBoarding) { inclusive = true }
            }
        }
    }
    if (!state.isCompleted) {
        OnBoardingScreenContent(
            pages = pages,
            state = state,
            onFinished = {
                viewModel.setOnboardingCompleted()
            },
            onPageChanged = { viewModel.updateCurrentPage(it) }
        )
    }
}

@Composable
fun OnBoardingScreenContent(
    modifier: Modifier = Modifier,
    pages : List<Page>,
    state: OnboardingUIState,
    onFinished: () -> Unit,
    onPageChanged: (Int) -> Unit
){

    val pagerState= rememberPagerState(initialPage = 0) { pages.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }
    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.colors.status.overlay)
                .statusBarsPadding()
        ) {
            if (pagerState.currentPage != pages.lastIndex) {
                TextButton(
                    modifier = Modifier.align(Alignment.Start),
                    onClick = onFinished
                ) {
                    Text(
                        text = stringResource(id = R.string.skip),
                        color = Theme.colors.primary,
                        style = Theme.textStyle.label.large
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { index ->
                OnboardingImage(
                    page = pages[index]
                )
            }
            BottomSection(
                page = pages[state.currentPage],
                onNextClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinished()
                    } else {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) { PageIndicator(pageSize = pages.size, currentPage = state.currentPage) }

        }
        Image(
            painter = painterResource(id = R.drawable.background_vectors),
            contentDescription = "Background Pattern",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Preview(
    name = "Galaxy J5 Prime",
    device = "spec:width=360dp,height=640dp,dpi=320"
)
@Composable
fun OnBoardingScreenPreview(){
    BasePreview {
        OnBoardingScreenContent(
            pages = onboardingPages(),
            onFinished = {},
            onPageChanged = {},
            state = OnboardingUIState()
        )
    }
}
