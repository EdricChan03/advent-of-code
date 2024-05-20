package com.edricchan.aoc.utils

object NumberUtils {
    /**
     * Converts the specified [list of bits (forming a binary number)][bin] to its decimal
     * form.
     * @param bin The list of bits (forming a binary number).
     */
    fun convertBinToDec(bin: List<Int>) = bin.joinToString("").toInt(2)
}
