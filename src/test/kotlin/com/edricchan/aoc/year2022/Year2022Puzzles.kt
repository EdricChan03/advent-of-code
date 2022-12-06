package com.edricchan.aoc.year2022

import com.edricchan.aoc.puzzle.dsl.puzzles

val puzzles = puzzles(2022) {
    puzzle(1, Day01(), 24000, 45000)
    puzzle(2, Day02(), 15, 12)
    puzzle(3, Day03(), 157, 70)
    puzzle(4, Day04(), 2, 4)
    puzzle(5, Day05(), "CMZ", "MCD")

    val day6Inputs = listOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
        "bvwbjplbgvbhsrlpgdmjqwftvncz",
        "nppdvjthqldpwncqszvftbrmjlhg",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    )
    val day6Outputs = listOf(7, 5, 6, 10, 11) to listOf(19, 23, 23, 29, 26)
    puzzle(6, ::Day06, day6Inputs, day6Outputs)
}
