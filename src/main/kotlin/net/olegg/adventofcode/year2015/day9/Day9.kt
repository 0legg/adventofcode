package net.olegg.adventofcode.year2015.day9

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.permutations
import net.olegg.adventofcode.utils.scan
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/9">Year 2015, Day 9</a>
 */
class Day9 : DayOf2015(9) {
    val pattern = "^\\b(\\w*)\\b to \\b(\\w*)\\b = (\\d*)$".toPattern()
    val edges = data.lines().flatMap {
        val matcher = pattern.matcher(it)
        matcher.matches()
        listOf(
                Pair(matcher.group(1), matcher.group(2)) to matcher.group(3).toInt(),
                Pair(matcher.group(2), matcher.group(1)) to matcher.group(3).toInt()
        )
    }.toMap()
    val cities = edges.keys.flatMap { listOf(it.first, it.second) }.distinct()

    override fun first(): String {
        return cities.permutations()
                .map {
                    it
                            .scan(Pair("", "")) { acc, value -> Pair(acc.second, value) }
                            .drop(1)
                            .map { edges[it] ?: 0 }
                            .sumBy { it }
                }.minBy { it }.toString()
    }

    override fun second(): String {
        return cities.permutations()
                .map {
                    it
                            .scan(Pair("", "")) { acc, value -> Pair(acc.second, value) }
                            .drop(1)
                            .map { edges[it] ?: 0 }
                            .sumBy { it }
                }.maxBy { it }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day9::class)
