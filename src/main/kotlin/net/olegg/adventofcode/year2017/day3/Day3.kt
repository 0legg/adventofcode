package net.olegg.adventofcode.year2017.day3

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import kotlin.math.abs

/**
 * @see <a href="http://adventofcode.com/2017/day/3">Year 2017, Day 3</a>
 */
class Day3 : DayOf2017(3) {
    override fun first(data: String): String {
        val position = data.trim().toInt()

        val square = (1 .. Int.MAX_VALUE step 2).first { it * it >= position }
        val relative = position - (square - 2) * (square - 2)
        val diff = (relative - 1) % (square - 1)

        return ((square / 2) + abs(diff - (square / 2 - 1))).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day3::class)
