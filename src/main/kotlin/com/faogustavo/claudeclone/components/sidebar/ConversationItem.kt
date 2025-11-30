package com.faogustavo.claudeclone.components.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.model.Conversation
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Text
import java.text.SimpleDateFormat
import java.util.*

/**
 * Individual conversation item in the sidebar list.
 */
@Composable
fun ConversationItem(
    conversation: Conversation,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current
    val backgroundColor = if (isSelected) colors.selection else colors.sidebar

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        // Conversation title
        Text(
            text = conversation.title,
            color = colors.text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Timestamp
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formatTimestamp(conversation.lastMessageAt),
            color = colors.secondaryText,
            maxLines = 1
        )
    }
}

/**
 * Formats a timestamp to a readable string.
 */
private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> "Just now"
        diff < 3600_000 -> "${diff / 60_000}m ago"
        diff < 86400_000 -> "${diff / 3600_000}h ago"
        diff < 604800_000 -> "${diff / 86400_000}d ago"
        else -> {
            val sdf = SimpleDateFormat("MMM d", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}
