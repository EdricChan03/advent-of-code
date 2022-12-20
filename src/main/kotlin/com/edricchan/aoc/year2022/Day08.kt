package com.edricchan.aoc.year2022

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import com.edricchan.aoc.utils.countTrue
import com.edricchan.aoc.year2022.day08.HeightMap
import com.edricchan.aoc.year2022.day08.HeightPoint
import com.edricchan.aoc.year2022.day08.toHeightMap

fun main() = solve { Day08(fileName = "") }
class Day08(fileName: String = "input.txt") :
    Puzzle<Int, Int>(year = 2022, day = 8, inputFileName = fileName) {
    // TODO: Use Sequence if possible
    private fun List<String>.asGrid() = map { it.toList().map(Char::digitToInt) }

    private val trees = input.asGrid()
        // Remove outer trees
//            .drop(1)
//            .dropLast(1)
        // Remove outer vert trees
//            .map { it.drop(1).dropLast(1) }
        // Map to HeightPoint
        .mapIndexed { rowI, ints ->
            ints.mapIndexed { colI, i -> HeightPoint(colI to rowI, i) }
        }
        .toHeightMap()

    override fun solvePartOne(): Int {
//        println(trees.asPrettyGrid())
        return trees.flatMap { line ->
            line.map { trees.isPointVisible(it.point) }
        }.countTrue()
    }

    override fun solvePartTwo(): Int {
//        println(trees.asPrettyGrid())
        return trees.flatMap { line ->
            line.map {
//                println("""
//                    Current point: $it
//                    L: ${trees.leftPoints(it.point)}
//                    VL: ${trees.viewableLeftPoints(it.point)}
//                    R: ${trees.rightPoints(it.point)}
//                    VR: ${trees.viewableRightPoints(it.point)}
//                    T: ${trees.topPoints(it.point)}
//                    VT: ${trees.viewableTopPoints(it.point)}
//                    VB: ${trees.viewableBottomPoints(it.point)}
//                    Score: ${trees.getPointScore(it.point)}
//                """.trimIndent())
//                println("-".repeat(30))
                trees.getPointScore(it.point)
            }
        }.max()
    }
}
