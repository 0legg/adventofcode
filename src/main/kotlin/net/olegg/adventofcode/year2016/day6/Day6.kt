package net.olegg.adventofcode.year2016.day6

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/6">Year 2016, Day 6</a>
 */
class Day6 : DayOf2016(6) {
    override fun first(data: String): Any? {
        return data.lines()
                .flatMap { it.toCharArray().mapIndexed { i, c -> i to c } }
                .groupBy { it.first }
                .mapValues { it.value.map { it.second } }
                .mapValues { it.value.groupBy { it } }
                .mapValues { it.value.mapValues { it.value.size } }
                .mapValues { it.value.maxBy { it.value }?.key ?: '?' }
                .toList()
                .sortedBy { it.first }
                .map { it.second }
                .joinToString(separator = "")
    }

    override fun second(data: String): Any? {
        return data.lines()
                .flatMap { it.toCharArray().mapIndexed { i, c -> i to c } }
                .groupBy { it.first }
                .mapValues { it.value.map { it.second } }
                .mapValues { it.value.groupBy { it } }
                .mapValues { it.value.mapValues { it.value.size } }
                .mapValues { it.value.minBy { it.value }?.key ?: '?' }
                .toList()
                .sortedBy { it.first }
                .map { it.second }
                .joinToString(separator = "")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day6::class)
