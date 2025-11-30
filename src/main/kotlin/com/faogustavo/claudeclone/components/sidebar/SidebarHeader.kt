package com.faogustavo.claudeclone.components.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.ClaudeCloneIcons
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Typography

/**
 * Header for the sidebar with "Past Conversations" title and new conversation button.
 */
@Composable
fun SidebarHeader(
    onNewConversation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Title with dropdown icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Past Conversations",
                color = colors.text
            )
            Icon(
                key = ClaudeCloneIcons.Actions.ChevronDown,
                contentDescription = "Dropdown",
                tint = colors.text
            )
        }

        // Right side: New conversation button
        IconButton(
            onClick = onNewConversation,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                key = ClaudeCloneIcons.Actions.Plus,
                contentDescription = "New conversation",
                tint = colors.text
            )
        }
    }
}
