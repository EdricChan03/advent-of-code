package com.edricchan.aoc.year2022.day07

import com.edricchan.aoc.model.tree.TreeNode
import com.edricchan.aoc.model.tree.toTreeNode

sealed class Command {
    abstract val name: String
    abstract val args: List<String>
    abstract val output: List<String>?

    data class Cd(val dir: String) : Command() {
        override val name = "cd"
        override val args: List<String>
            get() = listOf(dir)

        /** The `cd` command does not produce any output. */
        @Deprecated("The cd command does not produce any output", level = DeprecationLevel.HIDDEN)
        override val output: List<String>?
            get() = null

        fun resolve(path: List<String>) = when (dir) {
            // Go to the root directory
            "/" -> emptyList()
            // Go up one directory
            ".." -> path.dropLast(1)
            // Go to the specified directory
            else -> path + dir
        }
    }

    data class Ls(override val output: List<String>?) : Command() {
        override val name = "ls"

        override val args: List<String>
            get() = emptyList()

        /** The file entries that were produced by this command. */
        val entries by lazy {
            output?.map { it.toFileEntry() }
        }

        /** The file entries that were produced by this command as [RegularFile]s. */
        val files by lazy { entries?.map { it.toFile() } }
    }
}

/**
 * Converts the given list of strings to a command.
 *
 * It is assumed that the first line is the command that was executed,
 * and the lines after are the output of the command.
 */
fun List<String>.toCommand(): Command {
    val cmd = first()
    return if (cmd.startsWith("$ cd") || cmd.startsWith("cd")) {
        val path = Regex("""(\$ )?cd (.+)""").find(cmd)!!.groupValues.last()
        Command.Cd(path)
    } else {
        Command.Ls(drop(1))
    }
}

class Commands(private val commands: List<Command>) {
    private var currentPath = listOf<String>()
    private lateinit var rootTree: TreeNode<File>

    /** Executes the list of commands, and returns the result as a [tree][TreeNode]. */
    fun execute(): TreeNode<File> {
        commands.forEach { cmd ->
            when (cmd) {
                is Command.Cd -> {
                    currentPath = cmd.resolve(currentPath)
                    if (!::rootTree.isInitialized) rootTree = TreeNode(File.Directory("/"))
                }

                is Command.Ls -> {
                    var currTree = rootTree
                    val currPath = currentPath.toMutableList()
                    // Iterate through the current file path until we find the desired node
                    // to add the children to
                    while (currPath.isNotEmpty()) {
                        currTree = currTree.children.first { it.data.name == currPath.first() }
                        currPath.removeFirst()
                    }

                    val files = cmd.files.orEmpty()
                    currTree.addChildren(files.map { it.toTreeNode() })
                    (currTree.data as? File.Directory)?.addFiles(files)
                }
            }
        }
        return rootTree
    }
}

/** Executes the list of commands, and returns the result as a [tree][TreeNode]. */
fun List<Command>.executeCommands() = Commands(this).execute()
