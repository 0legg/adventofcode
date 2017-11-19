package net.olegg.adventofcode.year2016.day19

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016
import java.util.BitSet

/**
 * @see <a href="http://adventofcode.com/2016/day/18">Year 2016, Day 18</a>
 */
class Day19 : DayOf2016(19) {
    override fun first(data: String): String {
        val count = data.toInt()
        val elfs = BitSet(count)
        elfs.set(0, count)
        var position = 0
        repeat(count - 1) {
            val next = maxOf(elfs.nextSetBit(position + 1), elfs.nextSetBit(0))
            elfs.clear(next)
            position = maxOf(elfs.nextSetBit(next + 1), elfs.nextSetBit(0))
        }

        return (position + 1).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day19::class)
