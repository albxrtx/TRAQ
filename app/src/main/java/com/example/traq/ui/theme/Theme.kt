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
    primary = Color(0xFFF5F0FF),
    secondary = Color(0xFF1E90FF),
    background = Color(0xFF232323)
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF171717),
    secondary = Color(0xFF1E90FF),
    background = Color(0xFFE7E7E7)
)

@Composable
fun TraqTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Se adapta al tema oscuro si está habilitado
    content: @Composable () -> Unit
) {
    // Usamos MaterialTheme para envolver el contenido con el tema
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        content = content
    )
}