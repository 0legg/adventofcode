package net.olegg.adventofcode.year2015.day9

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.permutations
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/9">Year 2015, Day 9</a>
 */
class Day9 : DayOf2015(9) {
    companion object {
        private val PATTERN = "^\\b(\\w*)\\b to \\b(\\w*)\\b = (\\d*)$".toRegex()
    }

    private val edges = data
            .trim()
            .lines()
            .flatMap { line ->
                PATTERN.matchEntire(line)?.let { match ->
                    val (city1, city2, distance) = match.destructured
                    return@let listOf(
                            Pair(city1, city2) to distance.toInt(),
                            Pair(city2, city1) to distance.toInt()
                    )
                }.orEmpty()
            }
            .toMap()
    private val cities = edges.keys.flatMap { listOf(it.first, it.second) }.distinct()

    override fun first(data: String): Any? {
        return cities.permutations()
                .map { city ->
                    city
                            .zipWithNext()
                            .map { edges[it] ?: 0 }
                            .sumBy { it }
                }.minBy { it }
    }

    override fun second(data: String): Any? {
        return cities.permutations()
                .map { city ->
                    city
                            .zipWithNext()
                            .map { edges[it] ?: 0 }
                            .sumBy { it }
                }.maxBy { it }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day9::class)
