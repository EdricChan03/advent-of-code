package com.edricchan.aoc.year2021.day04

data class Board(
    val items: List<List<BoardItem>>
) {
    /** Checks if there is a complete row in this board. */
    fun hasCompleteRow() = items.any { item -> item.all { it.marked } }

    /** Checks if there is a complete column in this board. */
    fun hasCompleteCol(): Boolean {
        for (i in items.indices) {
            if (items.map { it[i] }.all { it.marked }) return true
        }
        return false
    }

    /** Marks the specified [number] in this board. */
    fun markNumber(number: Int) {
        items.forEach { row ->
            row.forEach {
                if (it.item == number) it.marked = true
            }
        }
    }

    /** Checks if this board has won. */
    fun hasWon(): Boolean {
        return hasCompleteRow() || hasCompleteCol()
    }

    /** Retrieves the score of this board given the [win number][winNumber]. */
    fun getScore(winNumber: Int): Int {
        val unmarkedItems = items.map { row -> row.filter { !it.marked } }
        val unmarkedSum = unmarkedItems.sumOf { row -> row.sumOf { it.item } }

        return unmarkedSum * winNumber
    }

    data class BoardItem(
        val item: Int,
        var marked: Boolean = false
    )

    companion object {
        /** Creates a board given the list of [numbers][items]. */
        fun fromNums(items: List<List<Int>>) = Board(
            items.map { item -> item.map { BoardItem(it) } }
        )
    }
}
