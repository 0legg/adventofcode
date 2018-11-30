package net.olegg.adventofcode.year2016.day2

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/2">Year 2016, Day 2</a>
 */
class Day2 : DayOf2016(2) {
    companion object {
        val KEYPAD1 = listOf(
                "00000",
                "01230",
                "04560",
                "07890",
                "00000"
        )

        val KEYPAD2 = listOf(
                "0000000",
                "0001000",
                "0023400",
                "0567890",
                "00ABC00",
                "000D000",
                "0000000"
        )

        val MOVES = mapOf(
                'U' to (0 to -1),
                'D' to (0 to 1),
                'L' to (-1 to 0),
                'R' to (1 to 0)
        )
    }

    override fun first(data: String): Any? {
        return data.lines().fold(Triple("", 2, 2)) { triple, command ->
            val point = command.toCharArray().fold(triple.second to triple.third) { pair, symbol ->
                (MOVES[symbol] ?: (0 to 0)).let {
                    Pair(pair.first + it.first, pair.second + it.second)
                }.let {
                    if (KEYPAD1[it.first][it.second] != '0') it else pair
                }
            }
            Triple(triple.first + "${KEYPAD1[point.second][point.first]}", point.first, point.second)
        }.first
    }

    override fun second(data: String): Any? {
        return data.lines().fold(Triple("", 2, 4)) { triple, command ->
            val point = command.toCharArray().fold(triple.second to triple.third) { pair, symbol ->
                (MOVES[symbol] ?: (0 to 0)).let {
                    Pair(pair.first + it.first, pair.second + it.second)
                }.let {
                    if (KEYPAD2[it.first][it.second] != '0') it else pair
                }
            }
            Triple(triple.first + "${KEYPAD2[point.second][point.first]}", point.first, point.second)
        }.first
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day2::class)
