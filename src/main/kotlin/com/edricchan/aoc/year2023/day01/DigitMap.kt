package com.edricchan.aoc.year2023.day01

val wordDigitMap = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

val digitMap = (1..9).associateBy { it.toString() }

val allDigitsMap = digitMap + wordDigitMap
