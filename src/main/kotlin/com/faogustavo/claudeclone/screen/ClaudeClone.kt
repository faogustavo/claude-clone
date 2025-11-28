package com.faogustavo.claudeclone.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastRoundToInt
import androidx.compose.ui.window.PopupPositionProvider
import com.faogustavo.claudeclone.ClaudeCloneIcons
import com.faogustavo.claudeclone.theme.ClaudeCloneTheme
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.jewel.foundation.theme.LocalTextStyle
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.ActionButton
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Popup
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextArea
import org.jetbrains.jewel.ui.component.Tooltip
import org.jetbrains.jewel.ui.icons.AllIconsKeys

@Composable
fun ClaudeClone(project: Project) {
    Box(
        modifier = Modifier.fillMaxSize().background(ClaudeCloneTheme.colors.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    ClaudeCloneIcons.ClaudeClone,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = ClaudeCloneTheme.colors.primaryAccent,
                )
                Text(
                    "Claude Clone",
                    modifier = Modifier.padding(start = 8.dp),
                )
            }

            // Center content
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        ClaudeCloneIcons.ClaudeClone,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = ClaudeCloneTheme.colors.primaryAccent,
                    )
                    Text(
                        "Use Claude Clone in the terminal to configure",
                        modifier = Modifier.padding(top = 16.dp),
                    )
                    Text(
                        "MCP servers. They'll work here, too!",
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }

            ClaudeCloneInput(project)
        }
    }
}

@Composable
private fun ClaudeCloneInput(project: Project, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val inputText = rememberTextFieldState("")

    val density = LocalDensity.current

    var showFilePopup by remember { mutableStateOf(false) }
    var textAreaBounds by remember { mutableStateOf(IntRect.Zero) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var searchQuery by remember { mutableStateOf("") }

    // File list for the popup - get real files from the project
    val fileList = remember(project) {
        val files = mutableListOf<String>()
        val projectFileIndex = ProjectRootManager.getInstance(project).fileIndex

        projectFileIndex.iterateContent { virtualFile ->
            if (!virtualFile.isDirectory) {
                val relativePath = projectFileIndex.getContentRootForFile(virtualFile)?.let { root ->
                    virtualFile.path.removePrefix(root.path).removePrefix("/")
                } ?: virtualFile.name
                files.add(relativePath)
            }
            true // Continue iteration
        }

        files.sorted()
    }

    // Monitor text changes to detect '@' character
    LaunchedEffect(inputText) {
        snapshotFlow { inputText.text.toString() to inputText.selection.start }
            .collectLatest { (text, cursorPosition) ->
                // Check if the text contains '@' and trigger popup
                if (cursorPosition > 0 && text.isNotEmpty()) {
                    // Find the last '@' before cursor position
                    val textBeforeCursor = text.substring(0, minOf(cursorPosition, text.length))
                    val lastAtIndex = textBeforeCursor.lastIndexOf('@')

                    if (lastAtIndex >= 0) {
                        // Check if there's no whitespace between '@' and cursor
                        val textAfterAt = textBeforeCursor.substring(lastAtIndex)
                        val hasWhitespace = textAfterAt.contains(Regex("\\s"))
                        showFilePopup = !hasWhitespace

                        // Extract search query (text after '@')
                        if (showFilePopup) {
                            searchQuery = textAfterAt.substring(1) // Remove '@' character
                        }
                    } else {
                        showFilePopup = false
                        searchQuery = ""
                    }
                } else {
                    showFilePopup = false
                    searchQuery = ""
                }

                // Reset selected index when popup is closed
                if (!showFilePopup) {
                    selectedIndex = -1
                }

                // Debug: Print popup state (temporary for testing)
                println("showFilePopup: $showFilePopup, searchQuery: '$searchQuery'")
            }
    }

    val borderWidth by animateDpAsState(if (isFocused) 1.dp else Dp.Unspecified)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(ClaudeCloneTheme.colors.secondaryBackground, RoundedCornerShape(8.dp))
            .border(borderWidth, ClaudeCloneTheme.colors.primaryAccent, RoundedCornerShape(8.dp))
            .onGloballyPositioned { coordinates ->
                val position = coordinates.positionInWindow()
                textAreaBounds = IntRect(
                    left = position.x.fastRoundToInt(),
                    top = position.y.fastRoundToInt(),
                    right = position.x.fastRoundToInt() + coordinates.size.width,
                    bottom = position.y.fastRoundToInt() + coordinates.size.height
                )
            }
            .then(modifier),
    ) {
        // File popup - render outside the input area
        if (showFilePopup && textAreaBounds != IntRect.Zero) {
            Popup(
                popupPositionProvider = object : PopupPositionProvider {
                    override fun calculatePosition(
                        anchorBounds: IntRect,
                        windowSize: IntSize,
                        layoutDirection: LayoutDirection,
                        popupContentSize: IntSize
                    ): IntOffset {
                        // Position popup above the text area
                        return IntOffset(
                            x = textAreaBounds.left,
                            y = textAreaBounds.top - popupContentSize.height - 8
                        )
                    }
                },
                onDismissRequest = { showFilePopup = false }
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = with(density) { textAreaBounds.width.toDp() })
                        .background(ClaudeCloneTheme.colors.secondaryBackground, RoundedCornerShape(8.dp))
                        .border(1.dp, ClaudeCloneTheme.colors.primaryAccent, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "Project Files",
                            style = LocalTextStyle.current.copy(
                                fontSize = LocalTextStyle.current.fontSize * 0.9f
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        // File list with selection indicator
                        fileList.forEachIndexed { index, fileName ->
                            val isSelected = index == selectedIndex
                            val itemInteractionSource = remember { MutableInteractionSource() }
                            val isHovered by itemInteractionSource.collectIsHoveredAsState()

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .hoverable(itemInteractionSource)
                                    .clickable {
                                        val currentText = inputText.text.toString()
                                        val cursorPosition = inputText.selection.start
                                        val textBeforeCursor = currentText.substring(0, minOf(cursorPosition, currentText.length))
                                        val lastAtIndex = textBeforeCursor.lastIndexOf('@')

                                        if (lastAtIndex >= 0) {
                                            val textBeforeAt = currentText.substring(0, lastAtIndex)
                                            val textAfterCursor = currentText.substring(cursorPosition)
                                            val newText = "$textBeforeAt@$fileName $textAfterCursor"

                                            inputText.edit {
                                                replace(0, length, newText)
                                            }

                                            showFilePopup = false
                                            selectedIndex = -1
                                        }
                                    }
                                    .background(
                                        if (isSelected || isHovered) ClaudeCloneTheme.colors.primaryAccent.copy(alpha = 0.2f)
                                        else ClaudeCloneTheme.colors.secondaryBackground,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    AllIconsKeys.FileTypes.Any_type,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    fileName,
                                    style = LocalTextStyle.current.copy(
                                        fontSize = LocalTextStyle.current.fontSize * 0.85f
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            TextArea(
                state = inputText,
                placeholder = { Text("⌘ Esc to focus or unfocus Claude") },
                undecorated = true,
                modifier = Modifier.fillMaxWidth()
                    .heightIn(max = 150.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .onPreviewKeyEvent { keyEvent ->
                        if (showFilePopup && keyEvent.type == KeyEventType.KeyDown) {
                            when (keyEvent.key) {
                                Key.DirectionDown -> {
                                    selectedIndex = if (selectedIndex == -1) 0 else (selectedIndex + 1) % fileList.size
                                    true
                                }
                                Key.DirectionUp -> {
                                    selectedIndex = if (selectedIndex == -1) 0 else if (selectedIndex - 1 < 0) fileList.size - 1 else selectedIndex - 1
                                    true
                                }
                                Key.Enter -> {
                                    if (selectedIndex >= 0 && selectedIndex < fileList.size) {
                                        val currentText = inputText.text.toString()
                                        val cursorPosition = inputText.selection.start
                                        val textBeforeCursor = currentText.substring(0, minOf(cursorPosition, currentText.length))
                                        val lastAtIndex = textBeforeCursor.lastIndexOf('@')

                                        if (lastAtIndex >= 0) {
                                            val textBeforeAt = currentText.substring(0, lastAtIndex)
                                            val textAfterCursor = currentText.substring(cursorPosition)
                                            val selectedFile = fileList[selectedIndex]
                                            val newText = "$textBeforeAt@$selectedFile $textAfterCursor"

                                            inputText.edit {
                                                replace(0, length, newText)
                                            }

                                            showFilePopup = false
                                            selectedIndex = -1
                                        }
                                    }
                                    true
                                }
                                else -> false
                            }
                        } else {
                            false
                        }
                    },
                decorationBoxModifier = Modifier.fillMaxWidth(),
                interactionSource = interactionSource,
            )
        }

        Divider(
            orientation = Orientation.Horizontal,
            modifier = Modifier.fillMaxWidth(),
            color = ClaudeCloneTheme.colors.background,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            ActionButton(onClick = {}) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(AllIconsKeys.Actions.Edit, contentDescription = null, modifier = Modifier.size(12.dp))
                    Text("Ask before edits", fontSize = LocalTextStyle.current.fontSize * 0.8f)
                }
            }

            Row {
                Tooltip(
                    tooltip = { Text("Enable Thinking") },
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            ClaudeCloneIcons.Actions.Thinking,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Tooltip(
                    tooltip = { Text("Commands") },
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            ClaudeCloneIcons.Actions.Command,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Tooltip(
                    tooltip = { Text("Submit") },
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.background(
                            ClaudeCloneTheme.colors.primaryAccent.copy(alpha = 0.33f),
                            RoundedCornerShape(4.dp)
                        )
                    ) {
                        Icon(ClaudeCloneIcons.Actions.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
