package com.dreamlab.casuskim.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    secondary = Color(0xFFE5E7EB),
    tertiary = Color(0xFFB22222),
    background = NightBlue,
    surface = SteelGray,
    onPrimary = NightBlue,
    onSecondary = SoftWhite,
    onTertiary = SoftWhite,
    onBackground = SoftWhite,
    onSurface = SoftWhite
)

private val LightColorScheme = lightColorScheme(
    primary = GoldAccent,
    secondary = Color(0xFFE5E7EB),
    tertiary = Color(0xFFB22222),
    background = Color(0xFFF8FAFC),
    surface = Color(0xFFF3F4F6),
    onPrimary = NightBlue,
    onSecondary = Color(0xFF111827),
    onTertiary = SoftWhite,
    onBackground = NightBlue,
    onSurface = NightBlue
)

@Composable
fun CasusKimTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}