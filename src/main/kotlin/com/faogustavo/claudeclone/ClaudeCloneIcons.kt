package com.faogustavo.claudeclone

import com.intellij.ui.IconManager
import org.jetbrains.jewel.ui.icon.PathIconKey

@Suppress("unused")
object ClaudeCloneIcons {
    @JvmField
    val ClaudeClone = PathIconKey("/icons/ClaudeClone.svg", javaClass)

    @JvmField
    val ToolWindow =
        IconManager.getInstance().getIcon("/icons/ClaudeCloneToolWindow.svg", javaClass.getClassLoader())

    object Actions {
        val Thinking = PathIconKey("/icons/Thinking.svg", javaClass)
        val Command = PathIconKey("/icons/Command.svg", javaClass)
        val Send = PathIconKey("/icons/Send.svg", javaClass)
        val ChevronDown = PathIconKey("/icons/ChevronDown.svg", javaClass)
        val Plus = PathIconKey("/icons/Plus.svg", javaClass)
        val Pencil = PathIconKey("/icons/Pencil.svg", javaClass)
    }
}
