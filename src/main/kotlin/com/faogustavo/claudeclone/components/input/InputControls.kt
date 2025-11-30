package com.faogustavo.claudeclone.components.input

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.ClaudeCloneIcons
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Checkbox
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text

/**
 * Controls row below the input field with checkbox, command menu, and send button.
 */
@Composable
fun InputControls(
    isLoading: Boolean,
    canSend: Boolean,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current
    var askBeforeEdits by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: "Ask before edits" checkbox
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = askBeforeEdits,
                onCheckedChange = { askBeforeEdits = it }
            )
            Text(
                text = "Ask before edits",
                color = colors.text
            )
            Icon(
                key = ClaudeCloneIcons.Actions.Pencil,
                contentDescription = "Edit",
                tint = colors.secondaryText,
                modifier = Modifier.size(16.dp)
            )
        }

        // Right side: Thinking indicator, command menu, send button
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thinking indicator
            if (isLoading) {
                Icon(
                    key = ClaudeCloneIcons.Actions.Thinking,
                    contentDescription = "Thinking",
                    tint = colors.secondaryText,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Command menu button (/)
            IconButton(
                onClick = { /* TODO: Implement command menu */ },
                modifier = Modifier.size(32.dp),
                enabled = false
            ) {
                Icon(
                    key = ClaudeCloneIcons.Actions.Command,
                    contentDescription = "Command menu",
                    tint = colors.secondaryText
                )
            }

            // Send button
            IconButton(
                onClick = onSend,
                modifier = Modifier.size(32.dp),
                enabled = canSend && !isLoading
            ) {
                Icon(
                    key = ClaudeCloneIcons.Actions.Send,
                    contentDescription = "Send message",
                    tint = if (canSend && !isLoading) colors.primaryAccent else colors.secondaryText
                )
            }
        }
    }
}
