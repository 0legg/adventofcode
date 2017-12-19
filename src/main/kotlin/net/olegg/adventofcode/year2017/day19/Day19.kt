package net.olegg.adventofcode.year2017.day19

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import kotlin.math.abs

/**
 * @see <a href="http://adventofcode.com/2017/day/19">Year 2017, Day 19</a>
 */
class Day19 : DayOf2017(19) {
    override fun first(data: String): String {
        val vector = listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
        )
        val map = data
                .lines()
                .map { it.toList() }

        var pos = map[0].indexOfFirst { it == '|' } to 0
        var dir = 0 to 1
        val result = StringBuilder()
        while (fit(map, pos)) {
            val char = map[pos.second][pos.first]
            if (char.isLetter()) {
                result.append(char)
            }
            val dirs = listOf(dir) + vector.filter { abs(it.first) != abs(dir.first) || abs(it.second) != abs(dir.second) }
            val newDir = dirs.firstOrNull {
                val next = pos.first + it.first to pos.second + it.second
                fit(map, next) && map[next.second][next.first] != ' '
            }
            if (newDir != null) {
                dir = newDir
                pos = pos.first + dir.first to pos.second + dir.second
            } else {
                pos = -1 to -1
            }
        }
        return result.toString()
    }

    override fun second(data: String): String {
        val vector = listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
        )
        val map = data
                .lines()
                .map { it.toList() }

        var pos = map[0].indexOfFirst { it == '|' } to 0
        var dir = 0 to 1
        var steps = 0
        while (fit(map, pos)) {
            steps += 1
            val dirs = listOf(dir) + vector.filter { abs(it.first) != abs(dir.first) || abs(it.second) != abs(dir.second) }
            val newDir = dirs.firstOrNull {
                val next = pos.first + it.first to pos.second + it.second
                fit(map, next) && map[next.second][next.first] != ' '
            }
            if (newDir != null) {
                dir = newDir
                pos = pos.first + dir.first to pos.second + dir.second
            } else {
                pos = -1 to -1
            }
        }
        return steps.toString()
    }

    fun fit(map: List<List<Any>>, pos: Pair<Int, Int>) = pos.second in map.indices && pos.first in map[pos.second].indices
}

fun main(args: Array<String>) = SomeDay.mainify(Day19::class)
