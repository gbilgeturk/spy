package com.dreamlab.casuskim.util

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.gradientBackground(): Modifier {
    return this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xCC000000), // siyah yarı saydam
                Color(0x990B1D36)  // koyu lacivert
            )
        )
    )
}