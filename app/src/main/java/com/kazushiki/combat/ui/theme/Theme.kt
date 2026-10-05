package com.kazushiki.combat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val KazushikiRed = Color(0xFFE31B23)
val KazushikiBackground = Color(0xFF080808)
val KazushikiSurface = Color(0xFF121214)
val KazushikiSurfaceAlt = Color(0xFF1A1A1D)
val KazushikiWarmWhite = Color(0xFFF4EFE6)
val KazushikiMuted = Color(0xFFA7A1A0)

private val KazushikiColors = darkColorScheme(
    primary = KazushikiRed,
    onPrimary = Color.White,
    background = KazushikiBackground,
    onBackground = KazushikiWarmWhite,
    surface = KazushikiSurface,
    onSurface = KazushikiWarmWhite,
    surfaceVariant = KazushikiSurfaceAlt,
    onSurfaceVariant = KazushikiMuted,
    outline = Color(0xFF353539)
)

@Composable
fun KazushikiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KazushikiColors,
        content = content
    )
}
