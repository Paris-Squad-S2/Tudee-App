package com.example.tudeeapp.presentation.screen.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.navigation.LocalNavController
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.onBoarding.components.OnBoardingPage
import com.example.tudeeapp.presentation.screen.onBoarding.components.PageIndicator
import kotlinx.coroutines.launch

@Composable
fun OnBoardScreen(
    viewModel: OnboardingViewModel,
    pages: List<Page>,
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            navController.navigate(Screens.Home("UserName"))
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
    state: OnboardingState,
    onFinished: () -> Unit,
    onPageChanged: (Int) -> Unit
){

    val pagerState= rememberPagerState(initialPage = 0) {
        pages.size
    }

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Column(
        modifier = modifier
    ) {
        val coroutineScope = rememberCoroutineScope()
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            OnBoardingPage(
                page = pages[index],
                onNextClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinished()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index + 1)
                        }
                    }
                },
                onSkipClick = {
                    onFinished()
                }
            )
        }
        Row(
            modifier = Modifier
                .background(Theme.colors.status.overlay)
                .fillMaxWidth()
                .padding(bottom = 60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            PageIndicator(
                pageSize = pages.size,
                currentPage = state.currentPage
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreenPreview(){
    OnBoardingScreenContent(
        pages = onboardingPages(),
        onFinished = {},
        onPageChanged = {},
        state = OnboardingState()
    )
}