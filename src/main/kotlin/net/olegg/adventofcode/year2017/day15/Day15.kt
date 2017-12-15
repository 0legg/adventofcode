package net.olegg.adventofcode.year2017.day15

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/15">Year 2017, Day 15</a>
 */
class Day15 : DayOf2017(15) {
    override fun first(data: String): String {
        val generators = data.trimIndent()
                .lines()
                .map { it.split("\\s+".toRegex()).last().toLong() }

        val genA = generateSequence(generators[0]) { (it * 16807L) % Int.MAX_VALUE.toLong() }
        val genB = generateSequence(generators[1]) { (it * 48271L) % Int.MAX_VALUE.toLong() }

        return genA.zip(genB)
                .take(40_000_000)
                .count { (it.first and 65535) == (it.second and 65535) }
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day15::class)
