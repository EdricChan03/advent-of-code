package com.edricchan.aoc

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.*
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SolverTest : DescribeSpec({
    // Override the default System.out/System.err
    val defaultOutPrintStream = System.out
    val testOutContentStream = ByteArrayOutputStream()
    val testOutPrintStream = PrintStream(testOutContentStream)

    val defaultErrStream = System.err
    val testErrContentStream = ByteArrayOutputStream()
    val testErrPrintStream = PrintStream(testErrContentStream)

    beforeTest {
        System.setOut(testOutPrintStream)
        System.setErr(testErrPrintStream)
    }

    afterTest {
        System.setOut(defaultOutPrintStream)
        System.setErr(defaultErrStream)
    }

    // Test puzzle data
    val (partOne, partTwo, year, day) = Arb.int().samples()
        .take(4).map { it.value }.toList()

    class TestPuzzle : Puzzle<Int, Int>(year = year, day = day) {
        override fun solvePartOne(): Int {
            return partOne
        }

        override fun solvePartTwo(): Int {
            return partTwo
        }
    }

    // TODO: Fix test
    xdescribe("getInput") {
        it("should retrieve the file data") {
            val bufferedReader = mockk<BufferedReader>()

            checkAll(10, Arb.int(2000..2040), Arb.int(1..31)) { year, day ->
                val fileData = Arb.string().samples().take(3).map { it.value }.toList()

                every {
                    bufferedReader.readLines()
                } returns fileData

                getInput(year, day) shouldBe fileData
            }
        }
    }

    describe("printResult") {
        it("should print the result of a puzzle") {
            printResult { TestPuzzle() }

            testOutContentStream.toString().trim().replace("\r\n", "\n") shouldBe """
            $day December $year:
            Part 1 result: ${TestPuzzle().solvePartOne()}
            Part 2 result: ${TestPuzzle().solvePartTwo()}
            """.trimIndent()
        }
    }

    describe("solve") {
        it("should call printResult") {
            mockkStatic(::solve)

            val puzzleFn: () -> Puzzle<*, *> = { TestPuzzle() }
            val benchmark = true
            solve(benchmark, puzzleFn)

            verify { solve(benchmark, puzzleFn) }
            verify { printResult(puzzleFn) }
        }
    }
})
