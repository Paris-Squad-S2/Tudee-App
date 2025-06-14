package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun Header(modifier: Modifier = Modifier ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Theme.colors.surfaceColors.surfaceHigh
            ),
            modifier = Modifier.size(48.dp)
        ) {
            Box {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_tudee),
                    contentDescription = stringResource(R.string.app_name),
                    tint = Color.Unspecified
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = Theme.textStyle.title.large.copy(fontFamily = cherryBomb),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(R.string.your_cute_helper_for_every_task),
                style = Theme.textStyle.label.small,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }

        //ToDo add switch button here
    }
}

@PreviewMultiDevices
@Composable
private fun HeaderPreview() {
    BasePreview {
        Header()
    }
}