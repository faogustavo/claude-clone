package com.faogustavo.claudeclone.toolWindow

import com.faogustavo.claudeclone.screen.ClaudeClone
import com.faogustavo.claudeclone.theme.ClaudeCloneTheme
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.jewel.bridge.addComposeTab

class ClaudeCloneToolWindowFactory :
    ToolWindowFactory,
    DumbAware {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow,
    ) {
        toolWindow.addComposeTab("Claude Clone") {
            ClaudeCloneTheme {
                ClaudeClone()
            }
        }
    }
}
