package com.faogustavo.claudeclone.components.autocomplete

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.model.FileReference
import org.jetbrains.jewel.ui.component.Text

/**
 * Autocomplete popup that displays a list of files.
 * Positioned above the input field.
 */
@Composable
fun FileAutocompletePopup(
    files: List<FileReference>,
    selectedIndex: Int,
    onFileSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (files.isEmpty()) {
        // Show "No files found" message
        Box(
            modifier = modifier
                .width(600.dp)
                .background(Color(0xFF2B2B2B), RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFF3C3C3C), RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "No files found",
                color = Color(0xFFC4A584)
            )
        }
        return
    }

    val listState = rememberLazyListState()

    // Auto-scroll to selected item
    LaunchedEffect(selectedIndex) {
        if (selectedIndex in files.indices) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    Box(
        modifier = modifier
            .width(600.dp)
            .heightIn(max = 400.dp) // Maximum height for ~10-12 items
            .background(Color(0xFF2B2B2B), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF3C3C3C), RoundedCornerShape(8.dp))
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(files) { index, file ->
                FileListItem(
                    file = file,
                    isSelected = index == selectedIndex,
                    onClick = { onFileSelect(index) }
                )
            }
        }
    }
}
