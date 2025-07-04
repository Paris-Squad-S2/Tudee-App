package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.text_style.cherryBomb
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun AppHeader(isDarkMode: Boolean, onToggleTheme: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.40f)
            ),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.40f)),
            modifier = Modifier.size(48.dp)
        ) {
                Icon(
                    modifier = Modifier
                        .offset(y = 5.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_tudee),
                    contentDescription = stringResource(R.string.app_name),
                    tint = Color.Unspecified
                )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = Theme.textStyle.title.large.copy(fontFamily = cherryBomb, color = Theme.colors.surfaceColors.onPrimaryColors.onPrimary),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(R.string.your_cute_helper_for_every_task),
                style = Theme.textStyle.label.small.copy(color = Theme.colors.surfaceColors.onPrimaryColors.onPrimary),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        SwitchThemeButton(toggled = isDarkMode, onToggle = onToggleTheme, modifier = Modifier)
    }
}

@PreviewMultiDevices
@Composable
private fun HeaderPreview() {
    var toggled by remember { mutableStateOf(false) }

    BasePreview {
        AppHeader(
            isDarkMode = toggled,
            onToggleTheme = {toggled = it},
            modifier = Modifier
        )
    }
}