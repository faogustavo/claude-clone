package com.faogustavo.claudeclone.components.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.viewmodel.ClaudeCloneViewModel
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.Orientation

/**
 * Complete sidebar panel containing header and conversation list.
 */
@Composable
fun SidebarPanel(
    viewModel: ClaudeCloneViewModel,
    modifier: Modifier = Modifier
) {
    val conversationsState = viewModel.conversationsState

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        SidebarHeader(
            onNewConversation = { viewModel.createNewConversation() }
        )

        // Divider
        Divider(
            orientation = Orientation.Horizontal,
            modifier = Modifier.fillMaxWidth()
        )

        // Conversation list
        ConversationList(
            conversations = conversationsState.conversations,
            selectedConversationId = conversationsState.selectedConversationId,
            onConversationClick = { conversationId ->
                viewModel.loadConversation(conversationId)
            },
            modifier = Modifier.weight(1f)
        )
    }
}
