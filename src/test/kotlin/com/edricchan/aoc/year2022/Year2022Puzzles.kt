package com.edricchan.aoc.year2022

import com.edricchan.aoc.puzzle.dsl.puzzle
import com.edricchan.aoc.puzzle.dsl.puzzles

val puzzles = puzzles(2022) {
    puzzle(1, Day01(), 24000, 45000)
    puzzle(2, Day02(), 15, 12)
    puzzle(3, Day03(), 157, 70)
    puzzle(4, Day04(), 2, 4)
    puzzle(5, Day05(), "CMZ", "MCD")

    // Demonstrates the answer DSLs
    puzzle(6, ::Day06) {
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" {
            partOneAns = 7
            partTwoAns = 19
        }
        withInput("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23)
        withInput("nppdvjthqldpwncqszvftbrmjlhg") {
            partOneAns = 6
            partTwoAns = 23
        }
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" answers {
            partOneAns = 10
            partTwoAns = 29
        }
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".answers(11, 26)
    }

    puzzle(7, Day07(), 95437, 24933642)
}
