package net.olegg.adventofcode.year2018.day3

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/3">Year 2018, Day 3</a>
 */
class Day3 : DayOf2018(3) {
    override fun first(data: String): Any? {
        val requests = data
                .trim()
                .lines()
                .mapNotNull { Request.fromString(it) }
        val width = requests.map { it.left + it.width }.max() ?: 0
        val height = requests.map { it.top + it.height }.max() ?: 0

        val field = Array(height) { Array(width) { 0 } }

        requests.forEach { request ->
            (request.top until request.top + request.height).forEach { y ->
                (request.left until request.left + request.width).forEach { x ->
                    field[y][x] += 1
                }
            }
        }

        return field.sumBy { row -> row.count { it > 1 } }
    }

    data class Request(
            val id: Int,
            val left: Int,
            val top: Int,
            val width: Int,
            val height: Int
    ) {
        companion object {
            private val PATTERN = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)".toPattern()

            fun fromString(data: String): Request? {
                val matcher = PATTERN.matcher(data)
                return if (matcher.matches()) {
                    val matches = matcher.toMatchResult()
                    val tokens = (1..matches.groupCount()).map { matches.group(it) }.map { it.toInt() }
                    Request(
                            id = tokens[0],
                            left = tokens[1],
                            top = tokens[2],
                            width = tokens[3],
                            height = tokens[4]
                    )
                } else {
                    null
                }
            }
        }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day3::class)
