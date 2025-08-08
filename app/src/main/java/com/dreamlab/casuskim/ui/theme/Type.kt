package com.dreamlab.casuskim.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dreamlab.casuskim.R

val SpyFont = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = SpyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = SoftWhite
    ),
    headlineMedium = TextStyle(
        fontFamily = SpyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = SoftWhite
    ),
    bodyLarge = TextStyle(
        fontFamily = SpyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = SoftWhite
    ),
    labelLarge = TextStyle(
        fontFamily = SpyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = SoftWhite
    )
)