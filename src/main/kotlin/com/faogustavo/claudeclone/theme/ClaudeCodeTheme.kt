package com.faogustavo.claudeclone.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.jetbrains.jewel.foundation.theme.JewelTheme

data class ClaudeCloneColors(
    val primaryAccent: Color,
    val darkOrange: Color,
    val coral: Color,
    val background: Color,
    val secondaryBackground: Color,
    val text: Color,
    val textCream: Color?,
    val sidebar: Color,
    val selection: Color,
    val secondaryText: Color,
    val functions: Color,
    val strings: Color,
    val numbers: Color,
    val types: Color,
    val black: Color,
) {
    companion object Companion {
        val Light =
            ClaudeCloneColors(
                primaryAccent = Color(0xFFC15F3C),
                darkOrange = Color(0xFFA14A2F),
                coral = Color(0xFFD97757),
                background = Color(0xFFFAF9F5),
                secondaryBackground = Color.White,
                text = Color(0xFF2C2C2C),
                textCream = null,
                sidebar = Color(0xFFF5F4ED),
                selection = Color(0x30C15F3C),
                secondaryText = Color(0xFF2C2C2C),
                functions = Color(0xFFFFB38A),
                strings = Color(0xFF98C379),
                numbers = Color(0xFFFF9966),
                types = Color(0xFF61AFEF),
                black = Color(0xFF000000),
            )

        val Dark =
            ClaudeCloneColors(
                primaryAccent = Color(0xFFE67D22),
                darkOrange = Color(0xFFA14A2F),
                coral = Color(0xFFD97757),
                background = Color(0xFF1A1815),
                secondaryBackground = Color(0xFF3c3c3c),
                text = Color(0xFFE8E6E3),
                textCream = Color(0xFFF5E6D3),
                sidebar = Color(0xFF201D18),
                selection = Color(0x30C15F3C),
                secondaryText = Color(0xFFC4A584),
                functions = Color(0xFFFFB38A),
                strings = Color(0xFF98C379),
                numbers = Color(0xFFFF9966),
                types = Color(0xFF61AFEF),
                black = Color(0xFF000000),
            )
    }
}

val LocalClaudeCloneColors = staticCompositionLocalOf { ClaudeCloneColors.Light }

@Composable
fun getClaudeCodeTextColor(): Color = LocalClaudeCloneColors.current.text

@Composable
fun getClaudeCodeSecondaryTextColor(): Color = LocalClaudeCloneColors.current.secondaryText

@Composable
fun getClaudeCodePrimaryAccent(): Color = LocalClaudeCloneColors.current.primaryAccent
