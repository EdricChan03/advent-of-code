package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.dsl.puzzle
import com.edricchan.aoc.puzzle.dsl.puzzles
import com.edricchan.aoc.puzzle.input.input
import java.time.Year

val puzzles = puzzles(Year.of(2024)) {
    puzzle(1, Day01(), 11, 31)
    puzzle(2, Day02(), 2, 4)
}
