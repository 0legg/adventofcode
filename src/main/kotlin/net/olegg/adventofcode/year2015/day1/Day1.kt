package net.olegg.adventofcode.year2015.day1

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015
import net.olegg.adventofcode.utils.scan

/**
 * @see <a href="http://adventofcode.com/2015/day/1">Year 2015, Day 1</a>
 */
class Day1 : DayOf2015(1) {
    val floors = data.map { 1 - 2 * (it.minus('(')) }

    override fun first(data: String): Any? {
        return floors.sum().toString()
    }

    override fun second(data: String): Any? {
        return (floors.scan(0) { acc, value -> acc + value }.indexOfFirst { it < 0 } + 1).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day1::class)
