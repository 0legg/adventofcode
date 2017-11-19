package net.olegg.adventofcode.year2016.day15

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/15">Year 2016, Day 15</a>
 */
class Day15 : DayOf2016(15) {
    val regex = "Disc #(\\d+) has (\\d+) positions; at time=(\\d+), it is at position (\\d+).".toRegex()

    override fun first(data: String): String {
        val discs = data.lines().filter { it.isNotBlank() }.map {
            regex.find(it)?.groupValues?.let { Triple(it[1].toInt(), it[2].toInt(), it[4].toInt()) }
        }.filterNotNull()

        return generateSequence(0) { it + 1}
                .filter { time ->
                    discs.all { (it.third + it.first + time) % it.second == 0 }
                }
                .first()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day15::class)
