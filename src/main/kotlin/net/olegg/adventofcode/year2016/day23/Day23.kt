package net.olegg.adventofcode.year2016.day23

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.AsmBunny
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/23">Year 2016, Day 23</a>
 */
class Day23 : DayOf2016(23) {
    override fun first(data: String): String {
        val program = data.lines().filter { it.isNotBlank() }
        val registers = IntArray(4).apply { this[0] = 7 }
        AsmBunny.eval(program, registers)
        return registers[0].toString()
    }

    override fun second(data: String): String {
        val program = data.lines().filter { it.isNotBlank() }
        val registers = IntArray(4).apply { this[0] = 12 }
        AsmBunny.eval(program, registers)
        return registers[0].toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day23::class)
