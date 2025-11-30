package com.faogustavo.claudeclone.components.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.model.Conversation
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Text

/**
 * Scrollable list of conversations in the sidebar.
 */
@Composable
fun ConversationList(
    conversations: List<Conversation>,
    selectedConversationId: String?,
    onConversationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    if (conversations.isEmpty()) {
        // Empty state
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No conversations yet",
                color = colors.secondaryText
            )
        }
    } else {
        // Conversation list
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(
                items = conversations,
                key = { it.id }
            ) { conversation ->
                ConversationItem(
                    conversation = conversation,
                    isSelected = conversation.id == selectedConversationId,
                    onClick = { onConversationClick(conversation.id) }
                )
            }
        }
    }
}
