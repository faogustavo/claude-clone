package com.faogustavo.claudeclone.model

/**
 * Represents the role of a message sender in a conversation.
 */
enum class MessageRole {
    /**
     * Message sent by the user.
     */
    USER,

    /**
     * Message sent by the AI assistant (Claude).
     */
    ASSISTANT,

    /**
     * System message (e.g., status updates, notifications).
     */
    SYSTEM
}
