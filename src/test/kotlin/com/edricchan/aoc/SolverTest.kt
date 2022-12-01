package com.edricchan.aoc

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import java.io.ByteArrayOutputStream
import java.io.File
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

    data class PuzzleData<O1, O2>(
        val puzzle: Puzzle<O1, O2>, val part1Ans: Int, val part2Ans: Int, val year: Int, val day: Int
    )

    fun getRandomPuzzle(): PuzzleData<Int, Int> {
        // Test data
        val (part1Ans, part2Ans) = Arb.int().samples().take(2).map { it.value }.toList()

        val (year, day) = Arb.positiveInt().samples().take(2).map { it.value }.toList()

        mockkStatic(::getInput)

        every { getInput(year, day) } returns listOf()

        class TestPuzzle : Puzzle<Int, Int>(year = year, day = day) {
            override fun solvePartOne(): Int {
                return part1Ans
            }

            override fun solvePartTwo(): Int {
                return part2Ans
            }
        }

        val puzzleMock = spyk(TestPuzzle())
        every { puzzleMock.input } returns listOf()

        return PuzzleData(puzzleMock, part1Ans, part2Ans, year, day)
    }

    fun getResourceLoader(file: File, expectedName: String) = ResourceLoader {
        it shouldBe expectedName
        file
    }

    fun getExpectedFileName(year: Int, day: Int, fileName: String) = "aoc/year$year/day$day/$fileName"

    describe("getInputAsFile") {
        it("should retrieve the file") {
            mockkStatic(::getInputAsFile)
            val file = tempfile()

            checkAll(
                // Year, day
                Arb.positiveInt(), Arb.positiveInt(),
                // File name
                Arb.stringPattern("\\w+\\.([A-z]\\d)+"),
            ) { year, day, fileName ->
                getInputAsFile(
                    year,
                    day,
                    fileName,
                    getResourceLoader(file, getExpectedFileName(year, day, fileName))
                ) shouldBe file
            }
        }
    }

    describe("getInput") {
        it("should retrieve the file data") {
            mockkStatic(::getInput)

            checkAll(
                // Year, day
                Arb.positiveInt(), Arb.positiveInt(),
                // File name
                Arb.stringPattern("\\w+\\.([A-z]\\d)+"),
                // File data
                Arb.list(Arb.string(minSize = 1))
            ) { year, day, fileName, fileData ->
                val tempFile = tempfile().apply {
                    writeText(fileData.joinToString("\n"))
                }

                getInput(
                    year, day, fileName,
                    getResourceLoader(tempFile, getExpectedFileName(year, day, fileName))
                ) shouldBe fileData
            }
        }
    }

    describe("printResult") {
        it("should print the result of a puzzle") {
            val puzzleData = getRandomPuzzle()

            printResult { puzzleData.puzzle }

            testOutContentStream.toString().trim().replace("\r\n", "\n") shouldBe """
            ${puzzleData.day} December ${puzzleData.year}:
            Part 1 result: ${puzzleData.puzzle.solvePartOne()}
            Part 2 result: ${puzzleData.puzzle.solvePartTwo()}
            """.trimIndent()
        }
    }

    describe("solve") {
        it("should call printResult") {
            val puzzleData = getRandomPuzzle()

            mockkStatic(::solve)

            val puzzleFn: () -> Puzzle<*, *> = { puzzleData.puzzle }
            val benchmark = true
            solve(benchmark, puzzleFn)

            verify { solve(benchmark, puzzleFn) }
            verify { printResult(puzzleFn) }
        }
    }
})
