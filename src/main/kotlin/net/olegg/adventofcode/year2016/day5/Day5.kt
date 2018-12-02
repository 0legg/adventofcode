package net.olegg.adventofcode.year2016.day5

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.md5
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/5">Year 2016, Day 5</a>
 */
class Day5 : DayOf2016(5) {
    override fun first(data: String): Any? {
        return generateSequence(0) { it + 1 }
                .map { "$data$it".md5() }
                .filter { it.startsWith("00000") }
                .take(8)
                .map { it[5] }
                .joinToString(separator = "")
    }

    override fun second(data: String): Any? {
        val map = hashMapOf<Char, Char>()
        return generateSequence(0) { it + 1 }
                .map { "$data$it".md5() }
                .filter { it.startsWith("00000") }
                .filter { it[5] in '0'..'7' }
                .map { it[5] to it[6] }
                .filter { map[it.first] == null }
                .map { map[it.first] = it.second; it }
                .takeWhile { map.size < 8 }
                .toList()
                .sortedBy { it.first }
                .map { it.second }
                .joinToString(separator = "")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day5::class)
