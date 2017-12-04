package net.olegg.adventofcode.year2017.day4

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/4">Year 2017, Day 4</a>
 */
class Day4 : DayOf2017(4) {
    override fun first(data: String): String {
        return data.lines()
                .map { it.split("\\s".toRegex()) }
                .filter { it.size == it.toSet().size }
                .count()
                .toString()
    }

    override fun second(data: String): String {
        return data.lines()
                .map { it.split("\\s".toRegex()) }
                .map { it.map { String(it.toCharArray().sortedArray()) } }
                .filter { it.size == it.toSet().size }
                .count()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day4::class)
