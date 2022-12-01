package com.edricchan.aoc

import io.kotest.core.spec.style.DescribeSpec

abstract class AbstractPuzzleTests(private val puzzles: List<PuzzleTestData<*, *, *>>) :
    DescribeSpec({
        puzzles.forEach {
            include("Year ${it.puzzleMeta.year} ", puzzleTests(it))
        }
    })
