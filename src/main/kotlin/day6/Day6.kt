package day6

import someday.SomeDay

/**
 * Created by olegg on 12/19/15.
 */
class Day6: SomeDay(6) {
    val commands = data.split('\n')
    override fun first(): String {
        return commands.fold(Array(1000) { Array(1000) { false }}) { acc, value ->
            val matcher = "[\\D]*(\\d+),(\\d+)[\\D]*(\\d+),(\\d+)[\\D]*".toPattern().matcher(value)
            matcher.find()
            val points = (1..4).map { matcher.group(it).toInt() }
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
}

fun main(args: Array<String>) {
    val day = Day6()
    println(day.first())
}