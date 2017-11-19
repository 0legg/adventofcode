package net.olegg.adventofcode.year2016.day20

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016
import java.util.TreeSet

/**
 * @see <a href="http://adventofcode.com/2016/day/20">Year 2016, Day 20</a>
 */
class Day20 : DayOf2016(20) {
    val regex = "(\\d+)-(\\d+)".toRegex()

    override fun first(data: String): String {
        val banned = TreeSet<LongRange>(compareBy( { it.start }, { it.endInclusive } ))
        data.lines()
                .filter { it.isNotBlank() }
                .mapNotNull { regex.find(it)?.groupValues?.let { it[1].toLong() .. it[2].toLong() } }
                .forEach { mask ->
                    var range = mask
                    do {
                        val start = banned.find { range.start in it || range.start - 1 in it }?.also { banned.remove(it) }
                        val end = banned.find { range.endInclusive in it || range.endInclusive + 1 in it }?.also { banned.remove(it) }

                        range = listOf(start, range, end).filterNotNull().fold(LongRange.EMPTY) { acc, next ->
                            if (acc.isEmpty()) next else minOf(acc.start, next.start)..maxOf(acc.endInclusive, next.endInclusive)
                        }

                    } while (start != null || end != null)

                    banned.add(range)
                }


        return (if (banned.first().start > 0) 0 else banned.first().endInclusive + 1).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day20::class)
