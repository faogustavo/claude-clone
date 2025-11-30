package com.faogustavo.claudeclone.components.chat

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Text

/**
 * Header for the chat panel with Claude Code branding.
 */
@Composable
fun ChatHeader(
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Add Claude icon (orange star/sun icon) when available
            Text(
                text = "⭐",
                color = colors.primaryAccent
            )
            
            Text(
                text = "Claude Code",
                color = colors.text
            )
        }
    }
}
