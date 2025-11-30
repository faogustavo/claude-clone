package com.faogustavo.claudeclone.components.chat

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Text

/**
 * Empty state shown when there are no messages in the conversation.
 */
@Composable
fun EmptyState(
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: Add Claude space invader style icon when available
            Text(
                text = "👾",
                fontSize = 64.sp
            )
            
            Text(
                text = "Start a new conversation",
                color = colors.secondaryText
            )
            
            Text(
                text = "Type your message below to begin",
                color = colors.secondaryText
            )
        }
    }
}
