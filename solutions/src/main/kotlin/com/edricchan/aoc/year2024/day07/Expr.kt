package com.edricchan.aoc.year2024.day07

enum class OperandPart1 {
    Add {
        override fun calculate(left: Long, right: Long): Long = left + right
    },
    Multiply {
        override fun calculate(left: Long, right: Long): Long = left * right
    };

    abstract fun calculate(left: Long, right: Long): Long
}

enum class OperandPart2 {
    Add {
        override fun calculate(left: Long, right: Long): Long = left + right
    },
    Multiply {
        override fun calculate(left: Long, right: Long): Long = left * right
    },
    Concatenate {
        override fun calculate(left: Long, right: Long): Long = "$left$right".toLong()
    };

    abstract fun calculate(left: Long, right: Long): Long
}
