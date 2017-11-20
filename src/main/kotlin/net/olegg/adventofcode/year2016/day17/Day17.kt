package net.olegg.adventofcode.year2016.day17

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.md5
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/17">Year 2016, Day 17</a>
 */
class Day17 : DayOf2016(17) {
    override fun first(data: String): String {
        val start = Triple(1, 1, "")
        val moves = listOf(
                Triple(0, -1, 'U'),
                Triple(0, 1, 'D'),
                Triple(-1, 0, 'L'),
                Triple(1, 0, 'R')
        )

        val queue = mutableListOf(start)

        do {
            val (x, y, tail) = queue.removeAt(0)

            val next = moves
                    .zip("$data$tail".md5().substring(0, 4).toUpperCase().toList())
                    .filter { it.second in "BCDEF" }
                    .map { Triple(x + it.first.first, y + it.first.second, tail + it.first.third) }
                    .filter { it.first in 1..4 && it.second in 1..4 }

            queue.addAll(next)

            val found = next.find { it.first == 4 && it.second == 4 } != null
        } while (!found)

        return queue.first { it.first == 4 && it.second == 4 }.third
    }

    override fun second(data: String): String {
        val start = Triple(1, 1, "")
        val moves = listOf(
                Triple(0, -1, 'U'),
                Triple(0, 1, 'D'),
                Triple(-1, 0, 'L'),
                Triple(1, 0, 'R')
        )

        var best = 0
        val queue = mutableListOf(start)

        do {
            val (x, y, tail) = queue.removeAt(0)

            if (x == 4 && y == 4) {
                best = maxOf(best, tail.length)
                continue
            }

            val next = moves
                    .zip("$data$tail".md5().substring(0, 4).toUpperCase().toList())
                    .filter { it.second in "BCDEF" }
                    .map { Triple(x + it.first.first, y + it.first.second, tail + it.first.third) }
                    .filter { it.first in 1..4 && it.second in 1..4 }

            queue.addAll(next)
        } while (queue.isNotEmpty())

        return best.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day17::class)
