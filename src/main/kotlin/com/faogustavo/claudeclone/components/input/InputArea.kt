package com.faogustavo.claudeclone.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import com.faogustavo.claudeclone.viewmodel.ClaudeCloneViewModel
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider

/**
 * Complete input area containing file chips, text field, and controls.
 */
@Composable
fun InputArea(
    viewModel: ClaudeCloneViewModel,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current
    val chatState = viewModel.chatState
    val autocompleteState = viewModel.autocompleteState

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.background)
    ) {
        // Top divider
        Divider(
            orientation = Orientation.Horizontal,
            modifier = Modifier.fillMaxWidth()
        )

        // File context chips
        if (chatState.attachedFiles.isNotEmpty()) {
            FileContextChips(
                files = chatState.attachedFiles,
                onRemoveFile = { file -> viewModel.removeFileReference(file) }
            )
        }

        // Input text field
        InputTextField(
            text = chatState.inputText,
            onTextChange = { text -> viewModel.updateInputText(text) },
            autocompleteState = autocompleteState,
            onSelectNext = { viewModel.autocompleteSelectNext() },
            onSelectPrevious = { viewModel.autocompleteSelectPrevious() },
            onSelectCurrent = { viewModel.selectCurrentAutocompleteItem() },
            onHideAutocomplete = { viewModel.hideAutocomplete() },
            onFileSelect = { index -> viewModel.selectAutocompleteItem(index) }
        )

        // Input controls
        InputControls(
            isLoading = chatState.isLoading,
            canSend = chatState.inputText.isNotBlank(),
            onSend = { viewModel.sendMessage() }
        )
    }
}
