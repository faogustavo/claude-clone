package com.faogustavo.claudeclone.service

import com.faogustavo.claudeclone.model.FileReference
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile

/**
 * Service for querying and filtering project files.
 * Provides file search and autocomplete functionality.
 */
class ProjectFileService(private val project: Project) {

    /**
     * Gets all project files, excluding build directories, IDE files, and hidden files.
     */
    fun getAllProjectFiles(): List<VirtualFile> {
        val projectFileIndex = ProjectFileIndex.getInstance(project)
        val files = mutableListOf<VirtualFile>()

        projectFileIndex.iterateContent { file ->
            if (shouldIncludeFile(file, projectFileIndex)) {
                files.add(file)
            }
            true // Continue iteration
        }

        return files
    }

    /**
     * Searches files by name matching the query.
     * Implements fuzzy matching and sorts by relevance.
     */
    fun searchFiles(query: String): List<FileReference> {
        if (query.isBlank()) {
            return getAllProjectFiles()
                .take(100)
                .map { convertToFileReference(it) }
        }

        val queryLower = query.lowercase()
        val allFiles = getAllProjectFiles()

        // Score and filter files
        val scoredFiles = allFiles
            .mapNotNull { file ->
                val score = calculateRelevanceScore(file, queryLower)
                if (score > 0) file to score else null
            }
            .sortedByDescending { it.second }
            .take(50)
            .map { it.first }

        return scoredFiles.map { convertToFileReference(it) }
    }

    /**
     * Converts a VirtualFile to a FileReference.
     */
    fun convertToFileReference(virtualFile: VirtualFile): FileReference {
        val projectBasePath = project.basePath ?: ""
        val path = virtualFile.path
        val relativePath = if (path.startsWith(projectBasePath)) {
            path.substring(projectBasePath.length).removePrefix("/")
        } else {
            path
        }

        return FileReference(
            path = relativePath,
            name = virtualFile.name,
            extension = virtualFile.extension ?: ""
        )
    }

    /**
     * Determines if a file should be included in the search results.
     */
    private fun shouldIncludeFile(file: VirtualFile, projectFileIndex: ProjectFileIndex): Boolean {
        // Only include files (not directories)
        if (file.isDirectory) return false

        // Exclude files not in project content
        if (!projectFileIndex.isInContent(file)) return false

        // Exclude hidden files
        if (file.name.startsWith(".")) return false

        // Exclude common build/IDE directories
        val path = file.path
        val excludedPaths = listOf(
            "/build/",
            "/target/",
            "/.gradle/",
            "/.idea/",
            "/node_modules/",
            "/out/",
            "/.git/"
        )
        if (excludedPaths.any { path.contains(it) }) return false

        return true
    }

    /**
     * Calculates relevance score for fuzzy matching.
     * Higher score = more relevant match.
     */
    private fun calculateRelevanceScore(file: VirtualFile, queryLower: String): Int {
        val nameLower = file.name.lowercase()
        val pathLower = file.path.lowercase()

        var score = 0

        // Exact name match (highest priority)
        if (nameLower == queryLower) {
            score += 1000
        }

        // Name starts with query
        if (nameLower.startsWith(queryLower)) {
            score += 500
        }

        // Name contains query
        if (nameLower.contains(queryLower)) {
            score += 100
        }

        // Path contains query
        if (pathLower.contains(queryLower)) {
            score += 10
        }

        // Fuzzy matching: check if all characters in query appear in order
        if (fuzzyMatch(nameLower, queryLower)) {
            score += 50
        }

        return score
    }

    /**
     * Simple fuzzy matching: checks if all characters in query appear in order in the target.
     */
    private fun fuzzyMatch(target: String, query: String): Boolean {
        var queryIndex = 0
        for (char in target) {
            if (queryIndex < query.length && char == query[queryIndex]) {
                queryIndex++
            }
        }
        return queryIndex == query.length
    }
}
