package com.example.beautyclub.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.ui.platform.LocalContext

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MyGlowColorScheme = lightColorScheme(
    primary          = Primary,
    onPrimary        = TextOnPrimary,
    primaryContainer = PrimaryDark,
    secondary        = Secondary,
    onSecondary      = TextOnPrimary,
    tertiary         = Accent,
    background       = Background,
    onBackground     = TextPrimary,
    surface          = Surface,
    onSurface        = TextPrimary,
    surfaceVariant   = Background,
    outline          = DividerColor,
    error            = PointRed,
)

@Composable
fun MyGlowBeautyTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = MyGlowColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}