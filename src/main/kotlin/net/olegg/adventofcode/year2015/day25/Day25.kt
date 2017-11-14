package net.olegg.adventofcode.year2015.day25

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/25">Year 2015, Day 25</a>
 */
class Day25 : DayOf2015(25) {
    override fun first(data: String): String {
        val matcher = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b".toPattern().matcher(data)
        if (matcher.find()) {
            val row = matcher.group(1).toInt()
            val column = matcher.group(2).toInt()
            return generateSequence(Triple(1, 1, 20151125L)) {
                Triple(
                        if (it.first != 1) it.first - 1 else it.second + 1,
                        if (it.first != 1) it.second + 1 else 1,
                        (it.third * 252533L) % 33554393L
                )
            }.first {
                it.first == row && it.second == column
            }.third.toString()
        }
        return ""
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day25::class)
