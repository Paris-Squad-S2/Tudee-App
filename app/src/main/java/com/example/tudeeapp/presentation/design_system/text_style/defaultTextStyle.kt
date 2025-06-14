package com.example.tudeeapp.presentation.design_system.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val defaultTextStyle = TudeeTextStyle(
    headline = SizedTextStyle(
        large = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.SemiBold, lineHeight = 30.sp, fontSize = 28.sp
        ), medium = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp, fontSize = 24.sp
        ), small = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, fontSize = 20.sp
        )
    ), title = SizedTextStyle(
        large = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Medium, lineHeight = 24.sp, fontSize = 20.sp
        ), medium = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Medium, lineHeight = 22.sp, fontSize = 18.sp
        ), small = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Medium, lineHeight = 20.sp, fontSize = 16.sp
        )
    ), body = SizedTextStyle(
        large = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Normal, lineHeight = 22.sp, fontSize = 18.sp
        ), medium = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Normal, lineHeight = 20.sp, fontSize = 16.sp
        ), small = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Normal, lineHeight = 17.sp, fontSize = 14.sp
        )
    ), label = SizedTextStyle(
        large = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Medium, lineHeight = 19.sp, fontSize = 16.sp
        ), medium = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Medium, lineHeight = 17.sp, fontSize = 14.sp
        ), small = TextStyle(
            fontFamily = inter, fontWeight = FontWeight.Medium, lineHeight = 16.sp, fontSize = 12.sp
        )
    )
)