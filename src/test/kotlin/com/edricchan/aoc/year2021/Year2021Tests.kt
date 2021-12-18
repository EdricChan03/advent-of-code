package com.edricchan.aoc.year2021

import com.edricchan.aoc.puzzleTests
import io.kotest.core.spec.style.DescribeSpec

class Year2021Tests : DescribeSpec({
    puzzles.forEach {
        include("Year ${it.puzzleMeta.year} ", puzzleTests(it))
    }
})
