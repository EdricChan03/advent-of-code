package com.edricchan.aoc.year2024.day05

import com.edricchan.aoc.utils.splitPair

data class Manual(
    val rules: List<Pair<Int, Int>>,
    val updates: List<List<Int>>
) {
    companion object {
        fun fromString(input: String): Manual {
            val (rulesRaw, updatesRaw) = input.trim().split("\n\n")

            val rules = rulesRaw.lines().map { it.splitPair("|", String::toInt) }

            val updates = updatesRaw.lines().map { it.split(",").map(String::toInt) }

            return Manual(
                rules, updates
            )
        }
    }

    val validUpdates by lazy {
        updates.filter { updateRow ->
            rules.all { (before, after) ->
                // Skip elements that don't exist
                if (before !in updateRow || after !in updateRow) return@all true

                updateRow.indexOf(before) < updateRow.indexOf(after)
            }
        }
    }

    val invalidUpdates by lazy {
        updates.filterNot { updateRow ->
            rules.all { (before, after) ->
                // Skip elements that don't exist
                if (before !in updateRow || after !in updateRow) return@all true

                updateRow.indexOf(before) < updateRow.indexOf(after)
            }
        }
    }

    fun isValidRow(row: List<Int>): Boolean {
        return rules.all { (before, after) ->
            // Skip elements that don't exist
            if (before !in row || after !in row) return@all true

            row.indexOf(before) < row.indexOf(after)
        }
    }
}
