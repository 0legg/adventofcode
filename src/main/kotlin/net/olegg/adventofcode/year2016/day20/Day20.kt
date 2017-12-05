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
        val banned = createBlacklist(data)

        return (if (banned.first().start > 0) 0 else banned.first().endInclusive + 1).toString()
    }

    override fun second(data: String): String {
        val banned = createBlacklist(data)

        return (banned.fold((-1L..-1L) to 0L) { acc, range ->
                    range to acc.second + (range.start - acc.first.endInclusive - 1)
                }.second +
                ((1L shl 32) - banned.last().endInclusive - 1)).toString()
    }

    fun createBlacklist(data: String): TreeSet<LongRange> {
        val banned = TreeSet<LongRange>(compareBy({ it.start }, { it.endInclusive }))
        data.lines()
                .filter { it.isNotBlank() }
                .mapNotNull { regex.find(it)?.groupValues?.let { it[1].toLong()..it[2].toLong() } }
                .forEach { mask ->
                    val join = banned.filter { mask.overlaps(it) } + listOf(mask)
                    banned.removeAll(join)

                    banned.add(join.fold(LongRange.EMPTY) { acc, next ->
                        if (acc.isEmpty()) next else minOf(acc.start, next.start)..maxOf(acc.endInclusive, next.endInclusive)
                    })
                }
        return banned
    }

    fun LongRange.extend(value: Long) = LongRange(start - value, endInclusive + value)

    fun LongRange.overlaps(other: LongRange): Boolean {
        return with(extend(1)) {
            other.first in this || other.endInclusive in this
        } || with(other.extend(1)) {
            this@overlaps.first in this || this@overlaps.endInclusive in this
        }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day20::class)
