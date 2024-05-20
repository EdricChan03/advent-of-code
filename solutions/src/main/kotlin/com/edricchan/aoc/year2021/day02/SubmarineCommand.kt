package com.edricchan.aoc.year2021.day02

import java.util.*

data class SubmarineCommand(
    val type: CommandType,
    val number: Int
) {
    constructor(command: String) : this(
        CommandType.valueOf(command.split(" ").first()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }),
        command.split(" ").last().toInt()
    )

    enum class CommandType {
        Forward, Down, Up
    }
}
