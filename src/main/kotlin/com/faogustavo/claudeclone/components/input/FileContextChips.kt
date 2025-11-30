package com.faogustavo.claudeclone.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.model.FileReference
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text

/**
 * Displays attached files as chips above the input field.
 */
@Composable
fun FileContextChips(
    files: List<FileReference>,
    onRemoveFile: (FileReference) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    if (files.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        files.forEach { file ->
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(colors.secondaryBackground)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // File icon and name
                Text(
                    text = "📎 ${file.name}",
                    color = colors.text
                )

                Spacer(modifier = Modifier.weight(1f))

                // Remove button
                IconButton(
                    onClick = { onRemoveFile(file) },
                    modifier = Modifier.size(16.dp)
                ) {
                    Text("×", color = colors.secondaryText)
                }
            }
        }
    }
}
