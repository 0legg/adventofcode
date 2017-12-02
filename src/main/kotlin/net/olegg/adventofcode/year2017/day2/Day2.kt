package net.olegg.adventofcode.year2017.day2

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/2">Year 2017, Day 2</a>
 */
class Day2 : DayOf2017(2) {
    override fun first(data: String): String {
        return data.lines()
                .map { it.split("\\s".toRegex()).map { it.toInt() } }
                .map { (it.max() ?: 0) - (it.min() ?: 0) }
                .sum()
                .toString()
    }

    override fun second(data: String): String {
        return data.lines()
                .map { it.split("\\s".toRegex()).map { it.toInt() } }
                .map { list -> list.flatMap { first -> list.filter { first % it == 0 }.filter { it != first }.map { first to it } } }
                .map { it.first() }
                .map { it.first / it.second }
                .sum()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day2::class)
