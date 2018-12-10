package net.olegg.adventofcode.year2018.day10

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/10">Year 2018, Day 10</a>
 */
class Day10 : DayOf2018(10) {
    companion object {
        private val PATTERN = "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>".toRegex()
        private const val HEIGHT = 16
    }
    override fun first(data: String): Any? {
        var points = data
                .trim()
                .lines()
                .mapNotNull { line ->
                    PATTERN.find(line)?.let { match ->
                        val (x, y, vx, vy) = match.destructured.toList().map { it.toInt() }
                        return@let (x to y) to (vx to vy)
                    }
                }

        do {
            points = points.map { it.copy(first = it.first + it.second) }
            val height = points
                    .map { it.first.second }
                    .let { (it.max() ?: 0) - (it.min() ?: 0) }
        } while (height > HEIGHT)

        val coords = points.map { it.first }

        val (xmin, xmax) = coords
                .map { it.first }
                .let { (it.min() ?: 0) to (it.max() ?: 0) }

        val (ymin, ymax) = coords
                .map { it.second }
                .let { (it.min() ?: 0) to (it.max() ?: 0) }

        val builder = StringBuilder("\n")
        (ymin..ymax).forEach { y ->
            (xmin..xmax).forEach { x ->
                builder.append(if (x to y in coords) "#" else ".")
            }
            builder.append("\n")
        }

        return builder.toString()
    }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

fun main(args: Array<String>) = SomeDay.mainify(Day10::class)
