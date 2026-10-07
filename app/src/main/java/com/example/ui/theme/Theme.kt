package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CivicNavyPrimaryDark,
    onPrimary = CivicNavyOnPrimaryDark,
    primaryContainer = CivicNavyPrimaryContainerDark,
    onPrimaryContainer = CivicNavyOnPrimaryContainerDark,
    secondary = CivicAzureSecondaryDark,
    onSecondary = CivicAzureOnSecondaryDark,
    secondaryContainer = CivicAzureSecondaryContainerDark,
    onSecondaryContainer = CivicAzureSecondaryContainerDark,
    tertiary = CivicGoldTertiaryDark,
    onTertiary = CivicGoldOnTertiaryDark,
    tertiaryContainer = CivicGoldTertiaryContainerDark,
    onTertiaryContainer = CivicGoldOnTertiaryDark,
    background = CivicSurfaceDark,
    onBackground = CivicOnSurfaceDark,
    surface = CivicSurfaceDark,
    onSurface = CivicOnSurfaceDark,
    surfaceVariant = CivicSurfaceVariantDark,
    onSurfaceVariant = CivicOnSurfaceVariantDark,
    outline = CivicOutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = CivicNavyPrimary,
    onPrimary = CivicNavyOnPrimary,
    primaryContainer = CivicNavyPrimaryContainer,
    onPrimaryContainer = CivicNavyOnPrimaryContainer,
    secondary = CivicAzureSecondary,
    onSecondary = CivicAzureOnSecondary,
    secondaryContainer = CivicAzureSecondaryContainer,
    onSecondaryContainer = CivicAzureOnSecondaryContainer,
    tertiary = CivicGoldTertiary,
    onTertiary = CivicGoldOnTertiary,
    tertiaryContainer = CivicGoldTertiaryContainer,
    onTertiaryContainer = CivicGoldOnTertiaryContainer,
    background = CivicSurfaceLight,
    onBackground = CivicOnSurfaceLight,
    surface = CivicSurfaceLight,
    onSurface = CivicOnSurfaceLight,
    surfaceVariant = CivicSurfaceVariantLight,
    onSurfaceVariant = CivicOnSurfaceVariantLight,
    outline = CivicOutlineLight
)

@Composable
fun MingachevirMunicipalityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep official municipal brand consistency
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
        typography = Typography,
        content = content
    )
}
