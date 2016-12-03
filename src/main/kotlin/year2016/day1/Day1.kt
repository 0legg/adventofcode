package year2016.day1

import year2015.DayOf2016
import java.lang.Math.abs

/**
 * Created by olegg on 12/2/16.
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

    override fun first(): String {
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
}

fun main(args: Array<String>) {
    val day = Day1()
    println(day.first())
}