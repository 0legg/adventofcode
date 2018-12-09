package net.olegg.adventofcode.year2018.day9

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/9">Year 2018, Day 9</a>
 */
class Day9 : DayOf2018(9) {
    companion object {
        private val PATTERN = "(\\d++) players; last marble is worth (\\d++) points".toRegex()
    }

    override fun first(data: String): Any? {
        val (players, points) = PATTERN.matchEntire(data.trim())?.destructured?.toList()?.map { it.toInt() }
                ?: throw IllegalArgumentException()

        val result = IntArray(players)
        val loop = mutableListOf(0)
        var position = 0

        (1..points).forEach { marble ->
            if (marble % 23 == 0) {
                val player = (marble - 1) % players
                result[player] += marble
                position = (position + loop.size - 7) % loop.size
                result[player] += loop.removeAt(position)
            } else {
                position = (position + 1) % loop.size + 1
                loop.add(position, marble)
            }
        }

        return result.max()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day9::class)
