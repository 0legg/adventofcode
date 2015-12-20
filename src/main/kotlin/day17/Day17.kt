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

    override fun second(): String {
        return (0..(1.shl(containers.size) - 1)).map { value ->
            containers.mapIndexed { index, container -> value.shr(index).and(1) * container }
        }.filter { it.sum() == 150 }.groupBy { it.count { it != 0 } }.minBy { it.key }?.value?.size?.toString() ?: ""
    }
}

fun main(args: Array<String>) {
    val day = Day17()
    println(day.first())
    println(day.second())
}