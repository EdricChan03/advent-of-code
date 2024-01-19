package com.edricchan.aoc.arb

import com.edricchan.aoc.PuzzleMeta
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.positiveInt
import java.time.Year

/**
 * Arbitrary [PuzzleMeta]s.
 * @param yearRange Constrains the generated [Year]s to be within this range.
 * @param maxDay Constrains the generated days to be below this value.
 * @see Arb.Companion.year
 * @see Arb.Companion.positiveInt
 */
fun Arb.Companion.puzzleMeta(
    yearRange: ClosedRange<Year> = YearRange,
    maxDay: Int = Int.MAX_VALUE
): Arb<PuzzleMeta> = arbitrary {
    val year = Arb.year(yearRange).bind()
    val day = Arb.positiveInt(maxDay).bind()
    PuzzleMeta(year, day)
}
