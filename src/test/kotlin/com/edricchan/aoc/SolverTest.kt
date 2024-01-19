package com.edricchan.aoc

import com.edricchan.aoc.arb.puzzleMeta
import com.edricchan.aoc.arb.year
import com.edricchan.aoc.puzzle.input.PuzzleInput
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
import java.nio.file.Path

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
        val puzzle: Puzzle<O1, O2>,
        val part1Ans: Int,
        val part2Ans: Int,
        val year: Int,
        val day: Int,
        val input: PuzzleInput
    )

    fun getRandomPuzzle(
        file: Path = tempfile().toPath()
    ): PuzzleData<Int, Int> {
        // Test data
        val (part1Ans, part2Ans) = Arb.int().take(2).toList()

        val year = Arb.year().single()
        val day = Arb.positiveInt().single()

        val input = PuzzleInput.File(file)

        class TestPuzzle : Puzzle<Int, Int>(
            year = year.value, day = day, inputData = input
        ) {
            override fun solvePartOne(): Int {
                return part1Ans
            }

            override fun solvePartTwo(): Int {
                return part2Ans
            }
        }

        val puzzleMock = spyk(TestPuzzle())
        every { puzzleMock.input } returns listOf()

        return PuzzleData(puzzleMock, part1Ans, part2Ans, year.value, day, input = input)
    }

    fun getResourceLoader(file: File, expectedName: String) = ResourceLoader {
        it shouldBe expectedName
        file
    }

    fun getExpectedFileName(year: Int, day: Int, fileName: String) = "aoc/year$year/day$day/$fileName"
    fun PuzzleMeta.getExpectedFileName(fileName: String) =
        getExpectedFileName(year = year.value, day = day, fileName = fileName)

    describe("getInputFile") {
        it("should retrieve the file") {
            mockkStatic(::getInputFile)
            val file = tempfile()

            checkAll(
                // Year, day
                Arb.positiveInt(), Arb.positiveInt(),
                // File name
                Arb.stringPattern("\\w+\\.([A-z]\\d)+"),
            ) { year, day, fileName ->
                val loader = getResourceLoader(file, getExpectedFileName(year, day, fileName))
                getInputFile(year, day, fileName, loader) shouldBe file
            }
        }
    }
    describe("PuzzleMeta.getInputFile") {
        it("should retrieve the file") {
            mockkStatic(PuzzleMeta::getInputFile)
            val file = tempfile()

            checkAll(
                // Metadata
                Arb.puzzleMeta(),
                // File name
                Arb.stringPattern("\\w+\\.([A-z]\\d)+"),
            ) { meta, fileName ->
                val loader = getResourceLoader(file, meta.getExpectedFileName(fileName))
                meta.getInputFile(fileName, loader) shouldBe file
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

                val loader = getResourceLoader(tempFile, getExpectedFileName(year, day, fileName))
                getInput(year, day, fileName, loader) shouldBe fileData
            }
        }
    }
    describe("PuzzleMeta.getInput") {
        it("should retrieve the file data") {
            mockkStatic(PuzzleMeta::getInput)

            checkAll(
                // Metadata
                Arb.puzzleMeta(),
                // File name
                Arb.stringPattern("\\w+\\.([A-z]\\d)+"),
                // File data
                Arb.list(Arb.string(minSize = 1))
            ) { meta, fileName, fileData ->
                val tempFile = tempfile().apply {
                    writeText(fileData.joinToString("\n"))
                }

                val loader = getResourceLoader(tempFile, meta.getExpectedFileName(fileName))
                meta.getInput(fileName, loader) shouldBe fileData
            }
        }
    }

    describe("printResult") {
        it("should print the result of a puzzle") {
            val puzzleData = getRandomPuzzle()

            printResult { puzzleData.puzzle }

            testOutContentStream.toString().trim().replace("\r\n", "\n") shouldBe """
            ${puzzleData.day} December ${puzzleData.year} (input - ${puzzleData.input.displayName}):
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
            verify { printBenchmarkedResult(puzzleFn) }
        }
    }
})
