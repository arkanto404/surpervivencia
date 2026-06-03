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
  primary = SurvivalPrimary,
  onPrimary = SurvivalOnPrimary,
  background = SurvivalBackground,
  onBackground = SurvivalTextPrimary,
  surface = SurvivalSurface,
  onSurface = SurvivalTextPrimary,
  surfaceVariant = SurvivalCardBg,
  onSurfaceVariant = SurvivalTextPrimary,
  outline = SurvivalBorder,
  error = SurvivalWarning
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force dark theme throughout for battery saving
  dynamicColor: Boolean = false, // Disable dynamic colors to preserve emergency orange identity
  content: @Composable () -> Unit,
) {
  val colorScheme = DarkColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
