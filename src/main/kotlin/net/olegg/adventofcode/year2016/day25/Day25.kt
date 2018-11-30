package net.olegg.adventofcode.year2016.day25

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.AsmBunny
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/25">Year 2016, Day 25</a>
 */
class Day25 : DayOf2016(25) {
    override fun first(data: String): Any? {
        val program = data.trim().lines()

        return generateSequence(0) { it + 1 }
                .map { value ->
                    val registers = IntArray(4).also { it[0] = value }
                    return@map value to AsmBunny.eval(program, registers)
                }
                .first { (_, signal) ->
                    signal
                            .take(1000)
                            .mapIndexed { index, value -> index % 2 == value % 2 }
                            .all { it }
                }
                .first
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day25::class)
