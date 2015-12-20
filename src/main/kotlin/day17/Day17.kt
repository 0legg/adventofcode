package day17

import someday.SomeDay

/**
 * Created by olegg on 12/20/15.
 */
class Day17: SomeDay(17) {
    val containers = data.lines().map { it.toInt() }
    override fun first(): String {
        return containers.fold(listOf(1) + Array(150) { 0 }.toList()) { acc, container ->
            acc.mapIndexed { index, value ->
                if (index < container) value else value + acc[index - container]
            }
        }.last().toString()
    }
}

fun main(args: Array<String>) {
    val day = Day17()
    println(day.first())
}