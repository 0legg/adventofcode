package net.olegg.adventofcode.year2016.day25

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.AsmBunny
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/25">Year 2016, Day 25</a>
 */
class Day25 : DayOf2016(25) {
    override fun first(data: String): String {
        val program = data.lines().filter { it.isNotBlank() }

        return generateSequence(0) { it + 1 }
                .map {
                    val registers = IntArray(4).apply { this[0] = it }
                    return@map it to AsmBunny.eval(program, registers)
                }
                .filter {
                    it.second.take(1000).mapIndexed { index, value -> index % 2 == value % 2 }.all { it }
                }
                .first()
                .first
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day25::class)
