package net.olegg.adventofcode.year2017.day10

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/10">Year 2017, Day 10</a>
 */
class Day10 : DayOf2017(10) {
    override fun first(data: String): String {
        return data.trimIndent()
                .split(",")
                .map { it.toInt() }
                .foldIndexed(List(256) { it } to 0) { index, acc, value ->
                    val prev = acc.first + acc.first
                    val curr = prev.subList(0, acc.second) + prev.subList(acc.second, acc.second + value).reversed() + prev.subList(acc.second + value, prev.size)
                    val next = (curr.subList(acc.first.size, acc.first.size + acc.second) + curr.subList(acc.second, acc.first.size))
                    return@foldIndexed next to ((acc.second + value + index) % acc.first.size)
                }
                .first
                .let { it[0] * it[1] }
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day10::class)
