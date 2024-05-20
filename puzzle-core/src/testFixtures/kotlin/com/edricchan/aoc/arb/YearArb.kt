package com.edricchan.aoc.arb

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import java.time.Year

val YearRange: ClosedRange<Year> = Year.of(Year.MIN_VALUE)..Year.of(Year.MAX_VALUE)

/**
 * Arbitrary [Year]s.
 *
 * @param range Constrains the generated [Year]s to be within this range.
 */
fun Arb.Companion.year(
    range: ClosedRange<Year> = YearRange
): Arb<Year> = arbitrary {
    val yearInt = Arb.int(range.start.value, range.endInclusive.value).bind()
    Year.of(yearInt)
}
