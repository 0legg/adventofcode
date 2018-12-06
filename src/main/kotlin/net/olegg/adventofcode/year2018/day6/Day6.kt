package net.olegg.adventofcode.year2018.day6

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018
import kotlin.math.abs

/**
 * @see <a href="http://adventofcode.com/2018/day/6">Year 2018, Day 6</a>
 */
class Day6 : DayOf2018(6) {
    companion object {
        private val PATTERN = "(\\d+), (\\d+)".toRegex()
    }

    override fun first(data: String): Any? {
        val points = data
                .trim()
                .lines()
                .mapNotNull { line ->
                    PATTERN.matchEntire(line)?.let { match ->
                        val (x, y) = match.destructured.toList().map { it.toInt() }
                        return@let x to y
                    }
                }

        val left = points.minBy { it.first }?.first ?: Int.MIN_VALUE
        val top = points.minBy { it.second }?.second ?: Int.MIN_VALUE
        val right = points.maxBy { it.first }?.first ?: Int.MAX_VALUE
        val bottom = points.maxBy { it.second }?.second ?: Int.MAX_VALUE

        val area = (top..bottom)
                .flatMap { y ->
                    (left..right).mapNotNull { x ->
                        val dist = points.map { abs(x - it.first) + abs(y - it.second) }
                        val best = dist.min() ?: 0
                        return@mapNotNull if (dist.count { it == best } == 1) dist.indexOfFirst { it == best } else null
                    }
                }

        return area
                .groupBy { it }
                .mapValues { it.value.size }
                .filterKeys { key ->
                    points[key].first !in listOf(left, right) && points[key].second !in listOf(top, bottom)
                }
                .maxBy { it.value }
                ?.value
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day6::class)
