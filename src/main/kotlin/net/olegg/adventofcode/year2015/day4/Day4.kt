package net.olegg.adventofcode.year2015.day4

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.md5
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/4">Year 2015, Day 4</a>
 */
class Day4 : DayOf2015(4) {
    override fun first(data: String): String {
        return generateSequence(1) { it + 1 }.first { (data + it.toString()).md5().startsWith("00000") }.toString()
    }

    override fun second(data: String): String {
        return generateSequence(1) { it + 1 }.first { (data + it.toString()).md5().startsWith("000000") }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day4::class)