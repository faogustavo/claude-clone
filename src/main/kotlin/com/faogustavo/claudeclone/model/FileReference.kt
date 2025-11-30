package com.faogustavo.claudeclone.model

/**
 * Represents a reference to a file in the project.
 *
 * @property path Full path to the file
 * @property name File name with extension
 * @property extension File extension (without the dot)
 */
data class FileReference(
    val path: String,
    val name: String,
    val extension: String
) {
    companion object {
        /**
         * Creates a FileReference from a file path.
         */
        fun fromPath(fullPath: String): FileReference {
            val name = fullPath.substringAfterLast('/')
            val extension = name.substringAfterLast('.', "")
            return FileReference(
                path = fullPath,
                name = name,
                extension = extension
            )
        }
    }

    /**
     * Returns the relative path from a given base path.
     */
    fun getRelativePath(basePath: String): String {
        return if (path.startsWith(basePath)) {
            path.removePrefix(basePath).removePrefix("/")
        } else {
            path
        }
    }

    /**
     * Returns a display name for the file (name without extension if extension exists).
     */
    fun getDisplayName(): String {
        return if (extension.isNotEmpty()) {
            name.removeSuffix(".$extension")
        } else {
            name
        }
    }
}
