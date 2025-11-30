package com.faogustavo.claudeclone.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faogustavo.claudeclone.CoroutineScopeHolder
import com.faogustavo.claudeclone.components.chat.ChatHeader
import com.faogustavo.claudeclone.components.chat.EmptyState
import com.faogustavo.claudeclone.components.chat.MessagesArea
import com.faogustavo.claudeclone.components.input.InputArea
import com.faogustavo.claudeclone.components.sidebar.SidebarPanel
import com.faogustavo.claudeclone.theme.LocalClaudeCloneColors
import com.faogustavo.claudeclone.viewmodel.ClaudeCloneViewModel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider

@Composable
fun ClaudeClone(project: Project) {
    val scopeHolder = project.service<CoroutineScopeHolder>()
    val viewModel = remember { 
        ClaudeCloneViewModel(project, scopeHolder.createScope("ClaudeCloneViewModel")) 
    }
    val colors = LocalClaudeCloneColors.current

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left Sidebar
        Box(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
                .background(colors.sidebar)
        ) {
            SidebarPanel(viewModel = viewModel)
        }

        // Divider
        Divider(
            orientation = Orientation.Vertical,
            modifier = Modifier.fillMaxHeight()
        )

        // Main Chat Panel
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(colors.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Chat header
                ChatHeader()

                // Messages area
                MessagesArea(
                    messages = viewModel.chatState.messages,
                    modifier = Modifier.weight(1f)
                )

                // Input area
                InputArea(viewModel = viewModel)
            }
        }
    }
}