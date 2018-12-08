package net.olegg.adventofcode.year2018.day8

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018
import java.util.ArrayDeque
import java.util.Deque

/**
 * @see <a href="http://adventofcode.com/2018/day/8">Year 2018, Day 8</a>
 */
class Day8 : DayOf2018(8) {
    override fun first(data: String): Any? {
        val numbers = data
                .trim()
                .split("\\s+".toRegex())
                .map { it.toInt() }

        return sumMetadata(ArrayDeque(numbers))
    }

    private fun sumMetadata(data: Deque<Int>): Int {
        val child = data.pop()
        val metadata = data.pop()

        return (0 until child).sumBy { sumMetadata(data) } + (0 until metadata).sumBy { data.pop() }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day8::class)
