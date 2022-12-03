package com.edricchan.aoc.year2022.day03

private val priLowList = ('a'..'z').toList()
private val priUpList = ('A'..'Z').toList()
val priorityList = (priLowList + priUpList).mapIndexed { index, c -> c to index + 1 }
