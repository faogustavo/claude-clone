package com.faogustavo.claudeclone.state

import com.faogustavo.claudeclone.model.FileReference
import com.faogustavo.claudeclone.model.Message

/**
 * State holder for chat functionality.
 *
 * @property messages List of messages in the current conversation
 * @property inputText Current text in the input field
 * @property attachedFiles List of files attached to the current message
 * @property isLoading Whether the assistant is currently processing/thinking
 */
data class ChatState(
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val attachedFiles: List<FileReference> = emptyList(),
    val isLoading: Boolean = false
) {
    /**
     * Adds a new message to the chat.
     */
    fun addMessage(message: Message): ChatState {
        return copy(messages = messages + message)
    }

    /**
     * Updates the input text.
     */
    fun updateInputText(text: String): ChatState {
        return copy(inputText = text)
    }

    /**
     * Adds a file reference to the attached files list.
     */
    fun addFileReference(file: FileReference): ChatState {
        return copy(attachedFiles = attachedFiles + file)
    }

    /**
     * Removes a file reference from the attached files list.
     */
    fun removeFileReference(file: FileReference): ChatState {
        return copy(attachedFiles = attachedFiles.filterNot { it == file })
    }

    /**
     * Clears the input text and attached files.
     */
    fun clearInput(): ChatState {
        return copy(
            inputText = "",
            attachedFiles = emptyList()
        )
    }

    /**
     * Sets the loading state.
     */
    fun setLoading(loading: Boolean): ChatState {
        return copy(isLoading = loading)
    }

    /**
     * Loads messages for a specific conversation.
     */
    fun loadMessages(newMessages: List<Message>): ChatState {
        return copy(messages = newMessages)
    }

    /**
     * Clears all messages (e.g., when switching conversations).
     */
    fun clearMessages(): ChatState {
        return copy(messages = emptyList())
    }
}
