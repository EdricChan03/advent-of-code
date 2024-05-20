package com.edricchan.aoc.year2022

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.year2022.day07.executeCommands
import com.edricchan.aoc.year2022.day07.getDirectories
import com.edricchan.aoc.year2022.day07.toCommand

fun main() = solve { Day07() }

class Day07 : Puzzle<Int, Int>(year = 2022, day = 7) {
    private val cmdOutput = rawInput
        .trimEnd()
        .split(Regex("""\n?\$\s+""")).drop(1)
        .map { it.lines() }

//    private val prettyTreeFn: (TreeNode<File>) -> String = { node ->
//        val (data, _, children) = node
//        "${data.name} (${
//            if (children.isNotEmpty()) "dir" else "file"
//        }, size=${data.size})"
//    }

    private fun executeCommands() = cmdOutput
        .map { it.toCommand() }.executeCommands()

    override fun solvePartOne(): Int {
        val maxTotalSize = 100000
//        println(cmdOutput)
        val tree = executeCommands()
//        println(tree.getPrettyTree(prettyFn = prettyTreeFn))
//        println(tree.withSize().getPrettyTree(prettyFn = prettyTreeFn))
        return tree.getDirectories().filter { it.size <= maxTotalSize }.sumOf { it.size }
    }

    override fun solvePartTwo(): Int {
        val totalSize = 70000000
        val minUnusedSize = 30000000

        // Get the currently used disk space
        val tree = executeCommands()
        val usedSize = tree.data.size
        val currUnusedSize = totalSize - usedSize
        val requiredUnusedSize = minUnusedSize - currUnusedSize
//        println("Required space: $requiredUnusedSize")

        val dirs = tree.getDirectories()
            .filter { it.size >= requiredUnusedSize }
            .sortedBy { it.size }
//        println(dirs.joinToString("\n") { "${it.name} - ${it.size}" })

        return dirs.first().size
    }
}
