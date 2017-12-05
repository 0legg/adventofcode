package net.olegg.adventofcode.year2017.day1

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/1">Year 2017, Day 1</a>
 */
class Day1 : DayOf2017(1) {
    override fun first(data: String): String {
        return data.trim()
                .let { it + it[0] }
                .windowed(2)
                .filter { it[0] == it[1] }
                .map { Character.digit(it[0], 10) }
                .sum()
                .toString()
    }

    override fun second(data: String): String {
        val source = data.trim()
        val shifted = data.substring(data.length / 2, data.length) + data.substring(0, data.length / 2)

        return source.zip(shifted)
                .filter { it.first == it.second }
                .map { Character.digit(it.first, 10) }
                .sum()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day1::class)
