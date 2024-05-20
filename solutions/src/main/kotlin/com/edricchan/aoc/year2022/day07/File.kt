package com.edricchan.aoc.year2022.day07

import com.edricchan.aoc.model.tree.TreeNode

sealed class File {
    abstract val name: String
    abstract val size: Int

    data class RegularFile(
        override val name: String,
        override val size: Int
    ) : File()

    data class Directory(
        override val name: String,
        private val files: MutableList<File> = mutableListOf()
    ) : File() {
        fun addFile(file: File) {
            files += file
        }

        fun addFiles(files: List<File>) {
            this.files += files
        }

        /** Retrieves the size of this directory's contents. */
        override val size: Int
            get() = files.sumOf { it.size }
    }
}

///**
// * Recursively calculates the size of the given [file tree node][TreeNode] and
// * its children, or the file's size if it is a regular file.
// */
//fun TreeNode<File>.calculateSize(): Int = data.size ?: children.sumOf { it.calculateSize() }

///**
// * Recursively sets the size of the given [file tree node][TreeNode] and its
// * children, or the file's size if it is a regular file.
// */
//fun TreeNode<File>.withSize(): TreeNode<File> {
//    return copy(
//        data = data.copy(name = data.name, size = calculateSize()),
//        children = children.map { it.withSize() }.toMutableList()
//    )
//}

/** Retrieves all directories from the [tree node][TreeNode]. */
fun TreeNode<File>.getDirectories(): List<File.Directory> {
    val dir = data as? File.Directory
    return listOfNotNull(dir) + children.flatMap { it.getDirectories() }
}
