package com.faogustavo.claudeclone.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.components.autocomplete.FileAutocompletePopup
import com.faogustavo.claudeclone.state.AutocompleteState
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import org.jetbrains.jewel.ui.component.Text

/**
 * Multi-line text input field for composing messages.
 * Displays autocomplete popup when triggered.
 */
@Composable
fun InputTextField(
    text: String,
    onTextChange: (String) -> Unit,
    autocompleteState: AutocompleteState,
    onSelectNext: () -> Unit,
    onSelectPrevious: () -> Unit,
    onSelectCurrent: () -> Unit,
    onHideAutocomplete: () -> Unit,
    onFileSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalClaudeCloneColors.current

    Column(modifier = modifier.fillMaxWidth()) {
        // Autocomplete popup (shown above input field)
        if (autocompleteState.isVisible) {
            FileAutocompletePopup(
                files = autocompleteState.files,
                selectedIndex = autocompleteState.selectedIndex,
                onFileSelect = onFileSelect,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }

        // Input field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.secondaryBackground)
                .padding(12.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp, max = 200.dp)
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown && autocompleteState.isVisible) {
                            when (keyEvent.key) {
                                Key.DirectionDown -> {
                                    onSelectNext()
                                    true
                                }
                                Key.DirectionUp -> {
                                    onSelectPrevious()
                                    true
                                }
                                Key.Enter -> {
                                    onSelectCurrent()
                                    true
                                }
                                Key.Escape -> {
                                    onHideAutocomplete()
                                    true
                                }
                                else -> false
                            }
                        } else {
                            false
                        }
                    },
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = colors.text
                ),
                cursorBrush = SolidColor(colors.primaryAccent),
                decorationBox = { innerTextField ->
                    Box {
                        if (text.isEmpty()) {
                            Text(
                                text = "Type a message...",
                                color = colors.secondaryText
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}
