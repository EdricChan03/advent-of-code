package com.edricchan.aoc.year2021

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import com.edricchan.aoc.utils.NumberUtils

fun main() = solve { Day03() }

class Day03 : Puzzle<Int, Int>(year = 2021, day = 3) {
    private val entries = input.map { entry ->
        entry.split("").filter { it.isNotBlank() }.map {
            it.toInt()
        }
    }

    private val entrySize = entries.first().size

    // Allow for a new list of entries to be specified to account for part 2
    private fun getBitCounts(currEntries: List<List<Int>> = entries): List<Pair<Int, Int>> {
        // bitCounts contains a count of each binary bit (uses a pair consisting of the
        // count of zeroes for the first value and the count of ones for the second)
        // per column.
        val bitCounts = MutableList(entrySize) { 0 to 0 }

        // Update bit counts
        currEntries.forEach { entry ->
            for (i in bitCounts.indices) {
                var (count0, count1) = bitCounts[i]
                val bit = entry[i]
                if (bit == 0) count0++ else count1++
                bitCounts[i] = count0 to count1
            }
        }

        return bitCounts.toList()
    }

    private fun getMostCommonBits(bitCounts: List<Pair<Int, Int>>): List<Int> {
        return bitCounts.map { (count0, count1) ->
            if (count0 > count1) 0 else 1
        }
    }

    private fun getLeastCommonBits(bitCounts: List<Pair<Int, Int>>): List<Int> {
        return bitCounts.map { (count0, count1) ->
            if (count0 < count1) 0 else 1
        }
    }

    override fun solvePartOne(): Int {
        val bitCounts = getBitCounts()

        // Calculate epsilon rate
        val epsiRate = NumberUtils.convertBinToDec(getMostCommonBits(bitCounts))

        // Calculate gamma rate
        // invertBin: Inverts bits (e.g. 00000000 -> 11111111)
        val gammaRate = NumberUtils.convertBinToDec(getLeastCommonBits(bitCounts))

        return epsiRate * gammaRate
    }

    override fun solvePartTwo(): Int {
        // NOTE: The bit count needs to be retrieved _every time_ a filter is done,
        // not done once.
        //val bitCounts = getBitCounts()

        // Find ratings
        var oxygenNums = entries
        var oxygenBitCounts = getBitCounts(oxygenNums)
        var co2Nums = entries
        var co2BitCounts = getBitCounts(co2Nums)

        for (index in 0 until entrySize) {
            // Filter oxygen generator entries
            val (oCount0, oCount1) = oxygenBitCounts[index]
            if (oxygenNums.size > 1) {
                // There's still more than 1 entry - keep filtering
                oxygenNums = if (oCount0 > oCount1) {
                    // 0 is more common
                    oxygenNums.filter { it[index] == 0 }
                } else {
                    // 1 is more common/0 and 1 are equal
                    oxygenNums.filter { it[index] == 1 }
                }
            }
            // Update oxygen generator bit count
            oxygenBitCounts = getBitCounts(oxygenNums)

            // Filter CO2 scrubber entries
            val (cCount0, cCount1) = co2BitCounts[index]
            if (co2Nums.size > 1) {
                // There's still more than 1 entry - keep filtering
                co2Nums = if (cCount0 <= cCount1) {
                    // 0 is less common/0 and 1 are equal
                    co2Nums.filter { it[index] == 0 }
                } else {
                    // 1 is less common
                    co2Nums.filter { it[index] == 1 }
                }
            }
            // Update CO2 scrubber bit count
            co2BitCounts = getBitCounts(co2Nums)
        }

        // Get the last binary numbers remaining
        val oxygenNum = NumberUtils.convertBinToDec(oxygenNums.first())
        val co2Num = NumberUtils.convertBinToDec(co2Nums.first())

        return oxygenNum * co2Num
    }
}
