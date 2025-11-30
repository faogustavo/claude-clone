package com.faogustavo.claudeclone.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.LocalContentColor


@Composable
fun ClaudeCloneTheme(content: @Composable () -> Unit) {
    val colors = if (JewelTheme.isDark) ClaudeCloneColors.Dark else ClaudeCloneColors.Light

    CompositionLocalProvider(
        LocalClaudeCloneColors provides colors,
        LocalContentColor provides colors.text,
    ) {
        content()
    }
}

object ClaudeCloneTheme {
    val colors: ClaudeCloneColors
        @Composable get() = LocalClaudeCloneColors.current
}