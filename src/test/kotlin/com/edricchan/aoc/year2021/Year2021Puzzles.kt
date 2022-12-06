package com.edricchan.aoc.year2021

import com.edricchan.aoc.puzzle.dsl.puzzles

val puzzles = puzzles(2021) {
    puzzle(1, Day01(), 7, 5)
    puzzle(2, Day02(), 150, 900)
    puzzle(3, Day03(), 198, 230)
    puzzle(4, Day04(), 4512, 1924)
    puzzle(5, Day05(), 5, 12)
    puzzle(6, Day06(), 5934, 26984457539)
    puzzle(7, Day07(), 37, 168)
    puzzle(8, Day08(), 26, 61229)
}
