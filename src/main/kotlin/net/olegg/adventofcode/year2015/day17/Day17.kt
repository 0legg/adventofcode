package net.olegg.adventofcode.year2015.day17

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/17">Year 2015, Day 17</a>
 */
class Day17 : DayOf2015(17) {
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

fun main(args: Array<String>) = SomeDay.mainify(Day17::class)
