package net.olegg.adventofcode.year2018.day1

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/1">Year 2018, Day 1</a>
 */
class Day1 : DayOf2018(1) {
    override fun first(data: String): Any? {
        return data
                .trim()
                .lines()
                .map { it.removePrefix("+") }
                .map { it.toInt() }
                .sum()
                .toString()
    }

    override fun second(data: String): Any? {
        val shifts = data
            .trim()
            .lines()
            .map { it.removePrefix("+") }
            .map { it.toLong() }

        (0..1_000_000_000).fold(0L to mutableSetOf(0L)) { (prev, history), i ->
            val next = prev + shifts[i % shifts.size]
            if (next in history) {
                return "$next"
            }
            history.add(next)
            return@fold (next to history)
        }

        return ""
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day1::class)
