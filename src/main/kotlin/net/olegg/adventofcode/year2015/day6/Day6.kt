package net.olegg.adventofcode.year2015.day6

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * Created by olegg on 12/19/15.
 */
class Day6 : DayOf2015(6) {
    val commands = data.lines()

    fun toPoints(command: String): List<Int> {
        val matcher = "[\\D]*(\\d+),(\\d+)[\\D]*(\\d+),(\\d+)[\\D]*".toPattern().matcher(command)
        matcher.find()
        return (1..4).map { matcher.group(it).toInt() }
    }

    override fun first(): String {
        return commands.fold(Array(1000) { Array(1000) { false } }) { acc, value ->
            val points = toPoints(value)
            (points[0]..points[2]).forEach { row ->
                (points[1]..points[3]).forEach { column ->
                    acc[row][column] = when {
                        value.startsWith("toggle") -> !acc[row][column]
                        value.startsWith("turn on") -> true
                        value.startsWith("turn off") -> false
                        else -> acc[row][column]
                    }
                }
            }
            acc
        }.sumBy { it.count { it } }.toString()
    }

    override fun second(): String {
        return commands.fold(Array(1000) { Array(1000) { 0 } }) { acc, value ->
            val points = toPoints(value)
            (points[0]..points[2]).forEach { row ->
                (points[1]..points[3]).forEach { column ->
                    acc[row][column] += when {
                        value.startsWith("toggle") -> 2
                        value.startsWith("turn on") -> 1
                        value.startsWith("turn off") && acc[row][column] > 0 -> -1
                        else -> 0
                    }
                }
            }
            acc
        }.sumBy { it.sum() }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day6::class)
