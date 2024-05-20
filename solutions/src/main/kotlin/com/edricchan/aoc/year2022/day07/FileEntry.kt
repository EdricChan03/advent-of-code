package com.edricchan.aoc.year2022.day07

enum class FileType {
    File,
    Directory
}

/** Represents a file entry as outputted by `ls`. */
// TODO: Remove this class
data class FileEntry(val type: FileType, val name: String, val size: Int? = null) {
//    fun toFile() = File(name, requireNotNull(size))
    fun toFile() = when (type) {
        FileType.File -> File.RegularFile(name, requireNotNull(size))
        FileType.Directory -> File.Directory(name, mutableListOf())
    }
}
//data class FileEntry(val type: FileType, val name: String, val size: Int? = null) {
//    fun toFile() = when (type) {
//        FileType.File -> File.RegularFile(name, requireNotNull(size))
//        FileType.Directory -> File.Directory(name, mutableListOf())
//    }
//}

/** Converts the receiver string to a [FileEntry]. */
fun String.toFileEntry(): FileEntry {
    val (dirOrSize, name) = split(" ")
    val (size, type) = if (dirOrSize.lowercase() == "dir") {
        // Entry is a directory
        null to FileType.Directory
    } else {
        dirOrSize.toInt() to FileType.File
    }
    return FileEntry(type, name, size)
}
