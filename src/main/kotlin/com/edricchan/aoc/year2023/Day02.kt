package com.edricchan.aoc.year2023

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.input.PuzzleInput
import com.edricchan.aoc.puzzle.PuzzleMeta
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.product
import com.edricchan.aoc.year2023.day02.Cubes
import com.edricchan.aoc.year2023.day02.toCubes
import java.time.Year

fun main() = solve(block = ::Day02)

private val testInput = PuzzleInput.ResourceFile("aoc/year2023/day2/test-input.txt")
private val defaultInput = PuzzleInput.Default(Day02.Meta)

class Day02(inputData: PuzzleInput = defaultInput) : Puzzle<Int, Int>(
    year = Meta.year.value,
    day = Meta.day,
    inputData = inputData
) {
    companion object {
        val Meta = PuzzleMeta(year = Year.of(2023), day = 2)
    }

    private val partOneBag = mapOf(
        Cubes.Color.Red to 12,
        Cubes.Color.Green to 13,
        Cubes.Color.Blue to 14
    )

    private fun Sequence<String>.associateRecords() = associate { line ->
        val (game, recordsRaw) = line.split(": ")
        val gameNo = game.drop("Game ".length).toInt()
        val hasRecords = recordsRaw.split("; ")
            .map { record ->
                record.split(", ").map { it.toCubes() }
                    .associate { it.kind to it.count }
            }
        gameNo to hasRecords
    }

    override fun solvePartOne() = useNotBlankInput { lines ->
        lines.associateRecords().filterValues { records ->
            records.all { record ->
                record.all {
                    it.key in partOneBag && partOneBag.getValue(it.key) >= it.value
                }
            }
        }.keys.sum()
    }

    override fun solvePartTwo() = useNotBlankInput { lines ->
        lines.associateRecords().mapValues { (_, records) ->
            Cubes.Color.entries.associateWith { color ->
                records.maxOf { it[color] ?: 0 }
            }
        }.values.sumOf { it.values.product() }
    }
}
