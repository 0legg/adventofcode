package net.olegg.adventofcode.year2016.day18

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/18">Year 2016, Day 18</a>
 */
class Day18 : DayOf2016(18) {

    val patterns = listOf(
            "\\^\\^\\.".toRegex(),
            "\\.\\^\\^".toRegex(),
            "\\^\\.\\.".toRegex(),
            "\\.\\.\\^".toRegex()
    )

    override fun first(data: String): String {
        return (1 until 40).fold(listOf(".$data.")) { acc, _ ->
            val traps = patterns.flatMap { it.findAll(acc.last()).map { it.range.start + 1 }.toList() }
            return@fold acc + acc.last().indices.map { if (traps.contains(it)) '^' else '.' }.joinToString(separator = "")
        }.sumBy { it.count { it == '.' } - 2 }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day18::class)
