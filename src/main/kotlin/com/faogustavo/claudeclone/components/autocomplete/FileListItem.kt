package com.faogustavo.claudeclone.components.autocomplete

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.model.FileReference
import org.jetbrains.jewel.ui.component.Text

/**
 * A single file item in the autocomplete dropdown.
 * Displays file icon, name, and path.
 */
@Composable
fun FileListItem(
    file: FileReference,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> Color(0xFF0D7ACC) // Blue selection color
        else -> Color.Transparent
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: File name
        Text(
            text = file.name,
            color = if (isSelected) Color.White else Color(0xFFE8E6E3),
            modifier = Modifier.weight(1f, fill = false)
        )

        // Right side: File path (secondary color)
        Text(
            text = file.path,
            color = if (isSelected) Color(0xFFCCCCCC) else Color(0xFFC4A584),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
