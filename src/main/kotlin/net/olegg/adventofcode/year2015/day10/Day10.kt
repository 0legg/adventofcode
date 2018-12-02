package net.olegg.adventofcode.year2015.day10

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015
import net.olegg.adventofcode.utils.series

/**
 * @see <a href="http://adventofcode.com/2015/day/10">Year 2015, Day 10</a>
 */
class Day10 : DayOf2015(10) {
    fun lookAndSay(source: String) = source.toList().series().map { "${it.size}${it.first()}" }.joinToString(separator = "")

    override fun first(data: String): Any? {
        return (1..40).fold(data) { acc, _ -> lookAndSay(acc) }.length.toString()
    }

    override fun second(data: String): Any? {
        return (1..50).fold(data) { acc, _ -> lookAndSay(acc) }.length.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day10::class)
