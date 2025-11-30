package com.faogustavo.claudeclone.model

/**
 * Represents a conversation in the Claude Clone application.
 *
 * @property id Unique identifier for the conversation
 * @property title Display title of the conversation
 * @property createdAt Timestamp when the conversation was created (milliseconds since epoch)
 * @property lastMessageAt Timestamp of the last message in the conversation (milliseconds since epoch)
 */
data class Conversation(
    val id: String,
    val title: String,
    val createdAt: Long,
    val lastMessageAt: Long
) {
    companion object {
        /**
         * Creates a new conversation with a generated ID and current timestamp.
         */
        fun create(title: String = "New Conversation"): Conversation {
            val now = System.currentTimeMillis()
            return Conversation(
                id = generateId(),
                title = title,
                createdAt = now,
                lastMessageAt = now
            )
        }

        private fun generateId(): String {
            return "${System.currentTimeMillis()}-${(0..9999).random()}"
        }
    }

    /**
     * Creates a copy of this conversation with an updated last message timestamp.
     */
    fun updateLastMessageTime(timestamp: Long = System.currentTimeMillis()): Conversation {
        return copy(lastMessageAt = timestamp)
    }

    /**
     * Creates a copy of this conversation with an updated title.
     */
    fun updateTitle(newTitle: String): Conversation {
        return copy(title = newTitle)
    }
}
