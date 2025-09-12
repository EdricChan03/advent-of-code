package com.edricchan.aoc.year2024

import com.edricchan.aoc.model.geom.point.Direction
import com.edricchan.aoc.model.geom.point.Point
import com.edricchan.aoc.model.grid.v2.Grid1D
import com.edricchan.aoc.model.grid.v2.toMutableGrid1D
import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.year2024.day06.Cell
import com.edricchan.aoc.year2024.day06.CellType
import com.edricchan.aoc.year2024.day06.Guard

fun main() = solve(block = ::Day06)

class Day06 : Puzzle<Int, Int>(
    year = 2024, day = 6,
//    inputData = """
//        ....#.....
//        .........#
//        ..........
//        ..#.......
//        .......#..
//        ..........
//        .#..^.....
//        ........#.
//        #.........
//        ......#...
//    """.trimIndent().input
) {
    private val grid = Grid1D.fromInput(input) { point, c ->
        Cell(point = point, type = CellType.fromChar(c))
    }
    private val guard = grid.rawList.first { it.type == CellType.Guard }

    override fun solvePartOne(): Int {
        var point = guard.point
        var direction = Direction.Up

        val mutableGrid = grid.toMutableGrid1D()

        while (true) {
            val oldPoint = point
            point = point moveIndex direction

            val newCell = mutableGrid.getOrNull(point) ?: break // Guard's at its final destination

            when (newCell.type) {
                CellType.Empty -> {
                    mutableGrid.update(point) { it.copy(type = CellType.Guard) }
                }

                CellType.Obstruction -> {
                    point = oldPoint
                    direction = direction.rotateClockwise90()
                }

                else -> {} // No-op for Guard
            }
        }

        return mutableGrid.rawList.count { it.type == CellType.Guard }
    }

    // TODO: This takes about ~4-6 minutes... there's probably a more optimal way
    //  of checking this
    override fun solvePartTwo(): Int {
        val start = guard.point
        var blockerGuard = Guard.initial(
            start = start
        )
        val addedObs = mutableSetOf<Point>()

        val mutableGrid = grid.toMutableGrid1D()

        while (true) {
//            val oldPoint = blockerGuard.point
//            println("-".repeat(50))
//            println("[B] Current point: $oldPoint")
//            blockerGuard = blockerGuard.withNewPoint { it.point moveIndex it.direction }
            val newPoint = blockerGuard.stepPoint()
//            println("[B] Stepped to $newPoint")

            val newCell = mutableGrid.getOrNull(newPoint) ?: break // Guard's at its final destination
//            println("[B] $newPoint has cell data $newCell")

            when (newCell.type) {
                CellType.Empty -> {
//                    println("[B] New point is empty, adding obstruction")
                    mutableGrid.update(newPoint) { it.copy(type = CellType.Obstruction) }
                    blockerGuard = blockerGuard.copy(point = newPoint)
                }

                CellType.Obstruction -> {
//                    println("[B] New point is an obstruction, rotating and skipping iteration")
                    blockerGuard = blockerGuard.rotated()
                    continue
                }

                else -> {} // No-op for Guard
            }

            var testerGuard = Guard.initial(start = start)
            while (true) {
//                println("+".repeat(25))
                var hasRotated = false
                val previousVisited = testerGuard.visitedPoints
                val newTesterPoint = testerGuard.stepPoint()
//                println("[T] Visiting new point $newTesterPoint")

                val newCell = mutableGrid.getOrNull(newTesterPoint) ?: run {
//                    println("[T] Final destination reached; no valid candidates (total = ${addedObs.size})")
                    // Guard's at its final destination
                    break
                }

//                println("[T] New point has cell data $newCell")
                when (newCell.type) {
                    CellType.Empty, CellType.Guard -> {
//                        println("[T] New point is empty, marking as visited")
                        testerGuard = testerGuard.copy(
                            point = newTesterPoint,
                            visitedPoints = previousVisited + (newTesterPoint to testerGuard.direction)
                        )
                    }

                    CellType.Obstruction -> {
//                        println("[T] New point is an obstruction, rotating")
                        testerGuard = testerGuard.rotated()
                        hasRotated = true
                    }
                }

//                println("[T] Current visited points: ${testerGuard.visitedPoints}")
//                println("[T] Current guard: $testerGuard")
//                println("[T] Previous visited (${previousVisited.size}): $previousVisited")
//                println("[T] Current visited (${testerGuard.visitedPoints.size}): ${testerGuard.visitedPoints}")
//                println("[T] Has rotated? $hasRotated")
                // Check for an infinite loop
                if (!hasRotated && previousVisited.size == testerGuard.visitedPoints.size) {
                    addedObs += newPoint
//                    println("Found candidate obstruction point at $newPoint (total = ${addedObs.size})")
                    break
                }
            }

            // Reset the added obstruction and move forward
//            println("[B] Resetting $newPoint to empty cell")
            mutableGrid.update(newPoint) { it.copy(type = CellType.Empty) }
        }
//        println("Found cells: $addedObs")

        return addedObs.count()
    }
}
