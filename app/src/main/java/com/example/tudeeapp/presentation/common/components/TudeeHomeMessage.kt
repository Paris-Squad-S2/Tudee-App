package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.home.HomeUiState
import com.example.tudeeapp.presentation.screen.home.SliderUiState

@Composable
fun TudeeHomeMessage(
    modifier: Modifier = Modifier,
    state: HomeUiState
) {


    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(state.sliderState.titleRes),
                    style = Theme.textStyle.title.small
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(state.sliderState.iconRes),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = LocalContext.current.getString(
                    state.sliderState.descriptionRes,
                    *state.sliderState.formatArgs
                ),
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.body
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .height(92.dp)
                .width(76.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .offset(y = 6.dp)
                    .clip(CircleShape)
                    .background(color = Theme.colors.primary.copy(alpha = 0.16f))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 12.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(state.sliderState.imageRes),
                    contentDescription = "robot",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@PreviewMultiDevices
@Composable
private fun SliderPreview() {
    BasePreview {
        Column {
            TudeeHomeMessage(
                state = HomeUiState(
                    sliderState = SliderUiState.GoodState(
                        count = 10,
                        total = 15
                    )
                )
            )
            TudeeHomeMessage(
                state = HomeUiState(
                    sliderState = SliderUiState.NothingState
                )
            )
            TudeeHomeMessage(
                state = HomeUiState(
                    sliderState = SliderUiState.ZeroProgressState(
                        count = 10,
                        total = 15
                    )
                )
            )
            TudeeHomeMessage(
                state = HomeUiState(
                    sliderState = SliderUiState.StayWorkingState(
                        done = 10,
                        total = 15
                    )
                )
            )

        }
    }
}
