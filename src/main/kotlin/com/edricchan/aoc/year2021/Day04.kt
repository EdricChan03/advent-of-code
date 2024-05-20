package com.edricchan.aoc.year2021

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.year2021.day04.Board

fun main() = solve { Day04() }

class Day04 : Puzzle<Int, Int>(year = 2021, day = 4) {
    private val numbers = input.first().split(",").map { it.toInt() }
    private val boards = input.slice(1..input.lastIndex)
        .filter { it.isNotEmpty() } // Remove empty lines
        // Split into 5 items each
        .chunked(5) { boards ->
            Board.fromNums(boards.map { board ->
                // Split into individual numbers
                board.split(" ")
                    .filter { it.isNotBlank() } // Remove elements that are not digits
                    .map { it.toInt() }
            })
        }

    override fun solvePartOne(): Int {
        var winBoard: Board? = null
        var winNum: Int = Int.MIN_VALUE

        numberLoop@ for (number in numbers) {
            for (board in boards) {
                board.markNumber(number)

                if (board.hasWon()) {
                    winBoard = board
                    winNum = number
                    break@numberLoop
                }
            }
        }

        return winBoard?.getScore(winNum) ?: Int.MIN_VALUE
    }

    override fun solvePartTwo(): Int {
        val winBoardsHistory = mutableListOf<Pair<Board, Int>>()
        var currBoards = boards

        numberLoop@ for (number in numbers) {
            for (board in currBoards) {
                board.markNumber(number)

                if (board.hasWon()) {
                    winBoardsHistory += board to number
                }
            }

            currBoards = boards - winBoardsHistory.map { it.first }.toSet()

            if (currBoards.isEmpty()) {
                // No more boards left
                break@numberLoop
            }
        }

        val (lastWinBoard, lastWinNum) = winBoardsHistory.last()
        return lastWinBoard.getScore(lastWinNum)
    }
}
