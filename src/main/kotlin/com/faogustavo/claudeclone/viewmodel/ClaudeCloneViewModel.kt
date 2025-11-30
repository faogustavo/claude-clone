package com.faogustavo.claudeclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.faogustavo.claudeclone.model.Conversation
import com.faogustavo.claudeclone.model.FileReference
import com.faogustavo.claudeclone.model.Message
import com.faogustavo.claudeclone.service.ProjectFileService
import com.faogustavo.claudeclone.state.AutocompleteState
import com.faogustavo.claudeclone.state.ChatState
import com.faogustavo.claudeclone.state.ConversationsState
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for the Claude Clone application.
 * Manages all application state and business logic.
 */
class ClaudeCloneViewModel(
    private val project: Project,
    private val coroutineScope: CoroutineScope
) {
    // Reactive state
    var conversationsState by mutableStateOf(ConversationsState())
        private set

    var chatState by mutableStateOf(ChatState())
        private set

    var autocompleteState by mutableStateOf(AutocompleteState())
        private set

    // Services
    private val fileService = ProjectFileService(project)

    // Storage for messages by conversation ID
    private val messagesByConversation = mutableMapOf<String, List<Message>>()

    /**
     * Creates a new conversation and selects it.
     */
    fun createNewConversation() {
        val newConversation = Conversation.create()
        conversationsState = conversationsState.addConversation(newConversation)
        chatState = chatState.clearMessages().clearInput()
        messagesByConversation[newConversation.id] = emptyList()
    }

    /**
     * Loads a conversation by its ID.
     */
    fun loadConversation(conversationId: String) {
        conversationsState = conversationsState.selectConversation(conversationId)
        val messages = messagesByConversation[conversationId] ?: emptyList()
        chatState = chatState.loadMessages(messages).clearInput()
    }

    /**
     * Sends a message in the current conversation.
     */
    fun sendMessage() {
        val selectedConversationId = conversationsState.selectedConversationId ?: return
        val content = chatState.inputText.trim()
        if (content.isEmpty()) return

        // Create user message
        val userMessage = Message.createUserMessage(
            conversationId = selectedConversationId,
            content = content,
            attachedFiles = chatState.attachedFiles
        )

        // Add user message to chat
        chatState = chatState.addMessage(userMessage).clearInput()

        // Update conversation last message time
        conversationsState.selectedConversation?.let { conversation ->
            val updatedConversation = conversation.updateLastMessageTime(userMessage.timestamp)
            conversationsState = conversationsState.updateConversation(updatedConversation)

            // Update title if this is the first message
            if (messagesByConversation[selectedConversationId]?.isEmpty() == true) {
                val title = generateConversationTitle(content)
                val withTitle = updatedConversation.updateTitle(title)
                conversationsState = conversationsState.updateConversation(withTitle)
            }
        }

        // Store message
        val currentMessages = messagesByConversation[selectedConversationId] ?: emptyList()
        messagesByConversation[selectedConversationId] = currentMessages + userMessage

        // Send mock assistant response (optional)
        sendMockAssistantResponse(selectedConversationId)
    }

    /**
     * Sends a mock assistant response after a delay.
     */
    private fun sendMockAssistantResponse(conversationId: String) {
        chatState = chatState.setLoading(true)

        coroutineScope.launch {
            delay(1500) // Simulate thinking time

            val assistantMessage = Message.createAssistantMessage(
                conversationId = conversationId,
                content = "This is a mock response. AI integration is not implemented yet."
            )

            // Only add if still on the same conversation
            if (conversationsState.selectedConversationId == conversationId) {
                chatState = chatState.addMessage(assistantMessage).setLoading(false)

                // Update conversation last message time
                conversationsState.selectedConversation?.let { conversation ->
                    val updatedConversation = conversation.updateLastMessageTime(assistantMessage.timestamp)
                    conversationsState = conversationsState.updateConversation(updatedConversation)
                }

                // Store message
                val currentMessages = messagesByConversation[conversationId] ?: emptyList()
                messagesByConversation[conversationId] = currentMessages + assistantMessage
            } else {
                chatState = chatState.setLoading(false)
            }
        }
    }

    /**
     * Generates a conversation title from the first message.
     */
    private fun generateConversationTitle(content: String): String {
        val maxLength = 50
        return if (content.length <= maxLength) {
            content
        } else {
            content.take(maxLength) + "..."
        }
    }

    /**
     * Updates the input text.
     */
    fun updateInputText(text: String) {
        chatState = chatState.updateInputText(text)
        
        // Check for '@' character to trigger autocomplete
        detectAutocompleteQuery(text)
    }

    /**
     * Detects if autocomplete should be triggered based on '@' character.
     */
    private fun detectAutocompleteQuery(text: String) {
        val atIndex = text.lastIndexOf('@')
        if (atIndex != -1) {
            val query = text.substring(atIndex + 1)
            // Only trigger if '@' is not followed by a space (still typing)
            if (!query.contains(' ')) {
                triggerAutocomplete(query)
            } else {
                autocompleteState = autocompleteState.hide()
            }
        } else {
            autocompleteState = autocompleteState.hide()
        }
    }

    /**
     * Triggers file autocomplete with the given query.
     */
    fun triggerAutocomplete(query: String) {
        autocompleteState = autocompleteState.show(query)
        // Search for files matching the query
        val matchingFiles = fileService.searchFiles(query)
        autocompleteState = autocompleteState.updateFiles(matchingFiles)
    }

    /**
     * Selects a file from the autocomplete list.
     */
    fun selectAutocompleteItem(index: Int) {
        val selectedFile = autocompleteState.files.getOrNull(index) ?: return
        
        // Add file to attached files
        chatState = chatState.addFileReference(selectedFile)
        
        // Remove '@query' from input text
        val atIndex = chatState.inputText.lastIndexOf('@')
        if (atIndex != -1) {
            val newText = chatState.inputText.substring(0, atIndex)
            chatState = chatState.updateInputText(newText)
        }
        
        // Hide autocomplete
        autocompleteState = autocompleteState.hide()
    }

    /**
     * Selects the currently highlighted autocomplete item.
     */
    fun selectCurrentAutocompleteItem() {
        selectAutocompleteItem(autocompleteState.selectedIndex)
    }

    /**
     * Removes a file reference from attached files.
     */
    fun removeFileReference(file: FileReference) {
        chatState = chatState.removeFileReference(file)
    }

    /**
     * Moves autocomplete selection to the next item.
     */
    fun autocompleteSelectNext() {
        autocompleteState = autocompleteState.selectNext()
    }

    /**
     * Moves autocomplete selection to the previous item.
     */
    fun autocompleteSelectPrevious() {
        autocompleteState = autocompleteState.selectPrevious()
    }

    /**
     * Hides the autocomplete popup.
     */
    fun hideAutocomplete() {
        autocompleteState = autocompleteState.hide()
    }

    /**
     * Deletes a conversation.
     */
    fun deleteConversation(conversationId: String) {
        conversationsState = conversationsState.deleteConversation(conversationId)
        messagesByConversation.remove(conversationId)
        
        // If the deleted conversation was selected, clear messages
        if (conversationsState.selectedConversationId == null) {
            chatState = chatState.clearMessages().clearInput()
        }
    }
}
