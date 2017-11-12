package year2015.day24

import year2015.DayOf2015

/**
 * Created by olegg on 12/23/15.
 */
class Day24 : DayOf2015(24) {
    val weights = data.lines().map { it.toLong() }
    override fun first(): String {
        val sum = weights.sum() / 3
        return subsets(sum, weights)
                .groupBy { it.size }
                .minBy { it.key }
                ?.value
                ?.map { it.fold(1L, Long::times) }
                ?.min()
                .toString()
    }

    override fun second(): String {
        val sum = weights.sum() / 4
        return subsets(sum, weights)
                .groupBy { it.size }
                .minBy { it.key }
                ?.value
                ?.map { it.fold(1L, Long::times) }
                ?.min()
                .toString()
    }
}

fun subsets(sum: Long, list: List<Long>): List<List<Long>> {
    if (sum < 0L) return listOf()
    if (sum == 0L) return listOf(listOf())
    if (list.size == 1) return if (sum == list[0]) listOf(list) else listOf()
    return subsets(sum, list.drop(1)) + subsets(sum - list[0], list.drop(1)).map { listOf(list[0]) + it }
}

fun main(args: Array<String>) {
    val day = Day24()
    println(day.first())
    println(day.second())
}