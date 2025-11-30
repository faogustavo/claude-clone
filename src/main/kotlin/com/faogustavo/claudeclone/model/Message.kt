package com.faogustavo.claudeclone.model

/**
 * Represents a message in a conversation.
 *
 * @property id Unique identifier for the message
 * @property conversationId ID of the conversation this message belongs to
 * @property content Text content of the message
 * @property role Role of the message sender (USER, ASSISTANT, or SYSTEM)
 * @property timestamp Timestamp when the message was created (milliseconds since epoch)
 * @property attachedFiles List of files referenced in this message
 */
data class Message(
    val id: String,
    val conversationId: String,
    val content: String,
    val role: MessageRole,
    val timestamp: Long,
    val attachedFiles: List<FileReference> = emptyList()
) {
    companion object {
        /**
         * Creates a new user message with a generated ID and current timestamp.
         */
        fun createUserMessage(
            conversationId: String,
            content: String,
            attachedFiles: List<FileReference> = emptyList()
        ): Message {
            return Message(
                id = generateId(),
                conversationId = conversationId,
                content = content,
                role = MessageRole.USER,
                timestamp = System.currentTimeMillis(),
                attachedFiles = attachedFiles
            )
        }

        /**
         * Creates a new assistant message with a generated ID and current timestamp.
         */
        fun createAssistantMessage(
            conversationId: String,
            content: String
        ): Message {
            return Message(
                id = generateId(),
                conversationId = conversationId,
                content = content,
                role = MessageRole.ASSISTANT,
                timestamp = System.currentTimeMillis(),
                attachedFiles = emptyList()
            )
        }

        /**
         * Creates a new system message with a generated ID and current timestamp.
         */
        fun createSystemMessage(
            conversationId: String,
            content: String
        ): Message {
            return Message(
                id = generateId(),
                conversationId = conversationId,
                content = content,
                role = MessageRole.SYSTEM,
                timestamp = System.currentTimeMillis(),
                attachedFiles = emptyList()
            )
        }

        private fun generateId(): String {
            return "${System.currentTimeMillis()}-${(0..9999).random()}"
        }
    }
}
