package theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = DarkPrimary,
  primaryVariant = DarkPrimary,
  onPrimary = DarkOnPrimary,
  secondary = DarkSecondary,
  secondaryVariant = DarkSecondary,
  onSecondary = DarkOnSecondary,
  background = DarkBackground,
  surface = DarkSurface,
  onSurface = DarkOnSurface,
  error = DarkError,
  onError = DarkOnPrimary
)

@Composable
fun TodoAppTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = DarkColorPalette,
    typography = TodoTypography,
    shapes = MaterialTheme.shapes,
    content = content
  )
}