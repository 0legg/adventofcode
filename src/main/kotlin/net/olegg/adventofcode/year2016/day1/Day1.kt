package net.olegg.adventofcode.year2016.day1

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016
import java.lang.Math.abs

/**
 * @see <a href="http://adventofcode.com/2016/day/1">Year 2016, Day 1</a>
 */
class Day1 : DayOf2016(1) {
    val MOVES = listOf(
            0 to 1,
            1 to 0,
            0 to -1,
            -1 to 0
    )

    val SHIFT = mapOf(
            'L' to -1,
            'R' to 1
    )

    override fun first(data: String): Any? {
        return data.split(", ")
                .map { it[0] to it.substring(1).toInt() }
                .fold(Triple(0, 0, 0)) { triple, command ->
                    val dir = (triple.third + MOVES.size + (SHIFT[command.first] ?: 0)) % MOVES.size
                    Triple(
                            triple.first + MOVES[dir].first * command.second,
                            triple.second + MOVES[dir].second * command.second,
                            dir)
                }
                .let { "${abs(it.first) + abs(it.second)}" }
                .toString()
    }

    override fun second(data: String): Any? {
        var visited = hashSetOf(0 to 0)

        data.split(", ")
                .map { it[0] to it.substring(1).toInt() }
                .fold(Triple(0, 0, 0)) { triple, command ->
                    val dir = (triple.third + MOVES.size + (SHIFT[command.first] ?: 0)) % MOVES.size
                    val steps = (1..command.second).map {
                        triple.first + MOVES[dir].first * it to triple.second + MOVES[dir].second * it
                    }
                    val hq = steps.firstOrNull { visited.contains(it) }
                    if (hq != null) {
                        return "${abs(hq.first) + abs(hq.second)}"
                    } else {
                        visited.addAll(steps)
                    }
                    Triple(
                            triple.first + MOVES[dir].first * command.second,
                            triple.second + MOVES[dir].second * command.second,
                            dir)
                }

        return "0"
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day1::class)
