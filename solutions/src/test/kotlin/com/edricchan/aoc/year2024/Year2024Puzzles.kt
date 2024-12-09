package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.dsl.puzzle
import com.edricchan.aoc.puzzle.dsl.puzzles
import com.edricchan.aoc.puzzle.input.input
import java.time.Year

val puzzles = puzzles(Year.of(2024)) {
    puzzle(1, Day01(), 11, 31)
    puzzle(2, Day02(), 2, 4)
    puzzle(3, Day03(), 161, 161)

    puzzle(3, ::Day03) {
        withInput(getTestInputPath("input-part2.txt").input, 161, 48)
    }

    puzzle(4, Day04(), 18, 9)
    puzzle(5, Day05(), 143, 123)
}
