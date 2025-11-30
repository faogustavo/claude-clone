package com.faogustavo.claudeclone.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.model.Message
import com.faogustavo.claudeclone.model.MessageRole
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Text
import java.text.SimpleDateFormat
import java.util.*

/**
 * Individual message item in the chat.
 */
@Composable
fun MessageItem(
    message: Message,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    val horizontalArrangement = when (message.role) {
        MessageRole.USER -> Arrangement.End
        MessageRole.ASSISTANT -> Arrangement.Start
        MessageRole.SYSTEM -> Arrangement.Center
    }

    val bubbleColor = when (message.role) {
        MessageRole.USER -> colors.primaryAccent.copy(alpha = 0.2f)
        MessageRole.ASSISTANT -> colors.secondaryBackground
        MessageRole.SYSTEM -> colors.secondaryText.copy(alpha = 0.1f)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = horizontalArrangement
    ) {
        Column(
            modifier = Modifier.widthIn(max = 600.dp)
        ) {
            // Message bubble
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(bubbleColor)
                    .padding(12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // File references if any
                    if (message.attachedFiles.isNotEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            message.attachedFiles.forEach { file ->
                                Text(
                                    text = "📎 ${file.name}",
                                    color = colors.secondaryText
                                )
                            }
                        }
                    }

                    // Message content
                    Text(
                        text = message.content,
                        color = colors.text
                    )
                }
            }

            // Timestamp
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatTimestamp(message.timestamp),
                color = colors.secondaryText,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

/**
 * Formats a timestamp to a readable string.
 */
private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
