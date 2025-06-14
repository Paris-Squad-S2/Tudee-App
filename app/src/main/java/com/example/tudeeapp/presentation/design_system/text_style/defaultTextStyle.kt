package com.example.tudeeapp.presentation.design_system.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tudeeapp.presentation.design_system.color.TudeeColors

val defaultTextStyle = TudeeTextStyle(
    headline = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.SemiBold, lineHeight = 30.sp, fontSize = 28.sp,
        ), medium = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp, fontSize = 24.sp
        ), small = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, fontSize = 20.sp
        )
    ), title = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Medium, lineHeight = 24.sp, fontSize = 20.sp
        ), medium = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Medium, lineHeight = 22.sp, fontSize = 18.sp
        ), small = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Medium, lineHeight = 20.sp, fontSize = 16.sp
        )
    ), body = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Normal, lineHeight = 22.sp, fontSize = 18.sp
        ), medium = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Normal, lineHeight = 20.sp, fontSize = 16.sp
        ), small = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Normal, lineHeight = 17.sp, fontSize = 14.sp
        )
    ), label = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Medium, lineHeight = 19.sp, fontSize = 16.sp,
        ), medium = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Medium, lineHeight = 17.sp, fontSize = 14.sp
        ), small = TextStyle(
            fontFamily = nunito, fontWeight = FontWeight.Medium, lineHeight = 16.sp, fontSize = 12.sp
        )
    )
)

fun generateTudeeTextStyle(colors: TudeeColors): TudeeTextStyle {
    return defaultTextStyle.copy(
        headline = defaultTextStyle.headline.copy(
            large = defaultTextStyle.headline.large.copy(color = colors.text.title),
            medium = defaultTextStyle.headline.medium.copy(color = colors.text.title),
            small = defaultTextStyle.headline.small.copy(color = colors.text.title)
        ),
        title = defaultTextStyle.title.copy(
            large = defaultTextStyle.title.large.copy(color = colors.text.title),
            medium = defaultTextStyle.title.medium.copy(color = colors.text.title),
            small = defaultTextStyle.title.small.copy(color = colors.text.title)
        ),
        body = defaultTextStyle.body.copy(
            large = defaultTextStyle.body.large.copy(color = colors.text.body),
            medium = defaultTextStyle.body.medium.copy(color = colors.text.body),
            small = defaultTextStyle.body.small.copy(color = colors.text.body)
        ),
        label = defaultTextStyle.label.copy(
            large = defaultTextStyle.label.large.copy(color = colors.text.hint),
            medium = defaultTextStyle.label.medium.copy(color = colors.text.hint),
            small = defaultTextStyle.label.small.copy(color = colors.text.hint)
        )
    )
}