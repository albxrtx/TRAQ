package com.example.traq.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColorScheme(
    background = Color(0xFF1D1D1D),
    primary = Color(0xFF282828),
    secondary = Color(0xFF3F3F3F),
    tertiary = Color(0xFF575757),
    onBackground = Color(0xFFF8F9FA)
)

private val LightColorPalette = lightColorScheme(
    background = Color(0xFFCED4DA),
    primary = Color(0xFFDEE2E6),
    secondary = Color(0xFFE9ECEF),
    tertiary = Color(0xFFF8F9FA),
    onBackground = Color(0xFF121212),
)

@Composable
fun TraqTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}