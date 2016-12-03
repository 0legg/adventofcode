package year2016.day2

import year2016.DayOf2016

/**
 * Created by olegg on 12/2/16.
 */
class Day2 : DayOf2016(2) {

    val KEYPAD = listOf(
            "00000",
            "01230",
            "04560",
            "07890",
            "00000"
    )

    val MOVES = mapOf(
            'U' to (0 to -1),
            'D' to (0 to 1),
            'L' to (-1 to 0),
            'R' to (1 to 0)
    )

    override fun first(): String {
        return data.split("\n").fold(Triple("", 2, 2)) { triple, command ->
            val point = command.toCharArray().fold(triple.second to triple.third) { pair, symbol ->
                (MOVES[symbol] ?: (0 to 0)).let {
                    Pair(pair.first + it.first, pair.second + it.second)
                }.let {
                    if (KEYPAD[it.first][it.second] != '0') it else pair
                }
            }
            Triple(triple.first + "${KEYPAD[point.second][point.first]}", point.first, point.second)
        }.first
    }
}

fun main(args: Array<String>) {
    val day = Day2()
    println(day.first())
}