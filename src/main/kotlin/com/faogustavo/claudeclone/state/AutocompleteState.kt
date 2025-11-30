package com.faogustavo.claudeclone.state

import com.faogustavo.claudeclone.model.FileReference

/**
 * State holder for file autocomplete functionality.
 *
 * @property isVisible Whether the autocomplete popup is visible
 * @property query Current search query
 * @property files List of files matching the query
 * @property selectedIndex Index of the currently selected file in the list
 */
data class AutocompleteState(
    val isVisible: Boolean = false,
    val query: String = "",
    val files: List<FileReference> = emptyList(),
    val selectedIndex: Int = 0
) {
    /**
     * Shows the autocomplete popup with the given query.
     */
    fun show(query: String = ""): AutocompleteState {
        return copy(
            isVisible = true,
            query = query,
            selectedIndex = 0
        )
    }

    /**
     * Hides the autocomplete popup and resets state.
     */
    fun hide(): AutocompleteState {
        return copy(
            isVisible = false,
            query = "",
            files = emptyList(),
            selectedIndex = 0
        )
    }

    /**
     * Updates the search query.
     */
    fun updateQuery(newQuery: String): AutocompleteState {
        return copy(
            query = newQuery,
            selectedIndex = 0
        )
    }

    /**
     * Updates the list of files.
     */
    fun updateFiles(newFiles: List<FileReference>): AutocompleteState {
        return copy(
            files = newFiles,
            selectedIndex = if (newFiles.isEmpty()) 0 else selectedIndex.coerceIn(0, newFiles.size - 1)
        )
    }

    /**
     * Selects the next file in the list.
     */
    fun selectNext(): AutocompleteState {
        if (files.isEmpty()) return this
        return copy(selectedIndex = (selectedIndex + 1).coerceAtMost(files.size - 1))
    }

    /**
     * Selects the previous file in the list.
     */
    fun selectPrevious(): AutocompleteState {
        if (files.isEmpty()) return this
        return copy(selectedIndex = (selectedIndex - 1).coerceAtLeast(0))
    }

    /**
     * Gets the currently selected file, or null if no file is selected.
     */
    fun getSelected(): FileReference? {
        return files.getOrNull(selectedIndex)
    }

    /**
     * Sets the selected index directly.
     */
    fun setSelectedIndex(index: Int): AutocompleteState {
        if (files.isEmpty()) return this
        return copy(selectedIndex = index.coerceIn(0, files.size - 1))
    }
}
