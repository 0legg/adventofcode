package net.olegg.adventofcode.year2015.day2

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/2">Year 2015, Day 2</a>
 */
class Day2 : DayOf2015(2) {
    val boxes = data.lines().map { it.split('x').map { it.toInt() }.sorted() }

    override fun first(data: String): Any? {
        return boxes.sumBy { 3 * it[0] * it[1] + 2 * it[0] * it[2] + 2 * it[1] * it[2] }.toString()
    }

    override fun second(data: String): Any? {
        return boxes.sumBy { 2 * (it[0] + it[1]) + it[0] * it[1] * it[2] }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day2::class)
