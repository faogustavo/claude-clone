package com.faogustavo.claudeclone.state

import com.faogustavo.claudeclone.model.Conversation

/**
 * State holder for conversations management.
 *
 * @property conversations List of all conversations
 * @property selectedConversationId ID of the currently selected conversation, null if none selected
 */
data class ConversationsState(
    val conversations: List<Conversation> = emptyList(),
    val selectedConversationId: String? = null
) {
    /**
     * Gets the currently selected conversation, or null if none selected.
     */
    val selectedConversation: Conversation?
        get() = selectedConversationId?.let { id ->
            conversations.find { it.id == id }
        }

    /**
     * Adds a new conversation to the list.
     */
    fun addConversation(conversation: Conversation): ConversationsState {
        return copy(
            conversations = conversations + conversation,
            selectedConversationId = conversation.id
        )
    }

    /**
     * Selects a conversation by its ID.
     */
    fun selectConversation(conversationId: String?): ConversationsState {
        return copy(selectedConversationId = conversationId)
    }

    /**
     * Deletes a conversation by its ID.
     */
    fun deleteConversation(conversationId: String): ConversationsState {
        val updatedConversations = conversations.filterNot { it.id == conversationId }
        val newSelectedId = if (selectedConversationId == conversationId) {
            updatedConversations.firstOrNull()?.id
        } else {
            selectedConversationId
        }
        return copy(
            conversations = updatedConversations,
            selectedConversationId = newSelectedId
        )
    }

    /**
     * Updates an existing conversation.
     */
    fun updateConversation(conversation: Conversation): ConversationsState {
        return copy(
            conversations = conversations.map {
                if (it.id == conversation.id) conversation else it
            }
        )
    }

    /**
     * Gets a conversation by its ID.
     */
    fun getConversation(conversationId: String): Conversation? {
        return conversations.find { it.id == conversationId }
    }
}
