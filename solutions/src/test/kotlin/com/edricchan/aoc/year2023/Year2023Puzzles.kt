package com.edricchan.aoc.year2023

import com.edricchan.aoc.puzzle.dsl.puzzle
import com.edricchan.aoc.puzzle.dsl.puzzles
import com.edricchan.aoc.puzzle.input.input
import java.time.Year

val puzzles = puzzles(Year.of(2023)) {
    puzzle(1, Day01(), 142, 142 /* same result... */)
    resourceLoader.getResourceAsPath("input-words.txt")?.let { path ->
        puzzle(1, ::Day01) {
            // Part 1 assumes that lines with no valid values are skipped
            withInput(path.input, 209, 281)
        }
    }

    puzzle(2, Day02(), 8, 2286)
}
