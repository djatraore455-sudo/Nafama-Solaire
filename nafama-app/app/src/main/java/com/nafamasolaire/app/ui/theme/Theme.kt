package com.nafamasolaire.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NafamaColorScheme = lightColorScheme(
    primary = VertMoyen,
    onPrimary = Color.White,
    secondary = OrSolaire,
    background = FondClair,
    surface = Color.White,
    onBackground = TexteFonce,
    onSurface = TexteFonce,
    error = Corail
)

@Composable
fun NafamaSolaireTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NafamaColorScheme,
        typography = NafamaTypography,
        content = content
    )
}
