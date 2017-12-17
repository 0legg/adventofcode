package net.olegg.adventofcode.year2017.day17

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/17">Year 2017, Day 17</a>
 */
class Day17 : DayOf2017(17) {
    override fun first(data: String): String {
        val cycle = mutableListOf(0)
        val step = data.trimIndent().toInt()

        val position = (1..2017).fold(0) { acc, value ->
            val insert = (acc + step) % cycle.size
            cycle.add(insert, value)
            return@fold insert + 1
        }

        return cycle[position % cycle.size].toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day17::class)
