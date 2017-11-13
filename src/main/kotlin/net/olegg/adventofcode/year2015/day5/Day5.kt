package net.olegg.adventofcode.year2015.day5

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * Created by olegg on 12/18/15.
 */
class Day5 : DayOf2015(5) {
    val strings = data.lines()
    override fun first(): String {
        return strings
                .filter { it.count { it in "aeiou" } >= 3 }
                .filterNot { it.contains("ab|cd|pq|xy".toRegex()) }
                .filter { it.contains("([a-z])\\1".toRegex()) }
                .size.toString()
    }

    override fun second(): String {
        return strings
                .filter { it.contains("([a-z]).\\1".toRegex()) }
                .filter { it.contains("([a-z]{2}).*\\1".toRegex()) }
                .size.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day5::class)
