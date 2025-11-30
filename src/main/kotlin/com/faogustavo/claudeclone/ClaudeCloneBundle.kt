package com.faogustavo.claudeclone

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

private const val BUNDLE = "messages.ClaudeCloneBundle"

object ClaudeCloneBundle {
    private val instance = DynamicBundle(ClaudeCloneBundle::class.java, BUNDLE)

    @JvmStatic
    fun message(
        key:
            @PropertyKey(resourceBundle = BUNDLE)
            String,
        vararg params: Any?,
    ): @Nls String = instance.getMessage(key, *params)

    @JvmStatic
    fun lazyMessage(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any?,
    ): Supplier<@Nls String> = instance.getLazyMessage(key, *params)
}
