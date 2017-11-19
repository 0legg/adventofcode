package net.olegg.adventofcode.year2016.day14

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.md5
import net.olegg.adventofcode.year2016.DayOf2016
import kotlin.coroutines.experimental.buildSequence

/**
 * @see <a href="http://adventofcode.com/2016/day/14">Year 2016, Day 14</a>
 */
class Day14 : DayOf2016(14) {

    val match3 = "(.)(\\1)(\\1)".toRegex()

    override fun first(data: String): String {
        return buildSequence {
            var i = 0
            while (true) {
                val curr = "$data$i".md5()
                match3.find(curr)?.let {
                    val next = it.groupValues[1].repeat(5)
                    if ((i + 1 .. i + 1000).any { "$data$it".md5().contains(next) }) {
                        yield(i)
                    }
                }
                i += 1
            } }
                .take(64)
                .last()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day14::class)
