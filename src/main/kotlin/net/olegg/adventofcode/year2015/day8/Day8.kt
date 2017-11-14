package net.olegg.adventofcode.year2015.day8

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/8">Year 2015, Day 8</a>
 */
class Day8 : DayOf2015(8) {
    val strings = data.lines()
    override fun first(data: String): String {
        return (strings.sumBy { it.length } - strings.sumBy {
            it
                    .replace("^\"".toRegex(), "")
                    .replace("\"$".toRegex(), "")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\")
                    .replace("\\\\x[0-9a-f]{2}".toRegex(), "#")
                    .length
        }).toString()
    }

    override fun second(data: String): String {
        return (strings.sumBy {
            it.map {
                when (it) {
                    '\"' -> "\\\""
                    '\\' -> "\\\\"
                    else -> "$it"
                }
            }.joinToString(prefix = "\"", postfix = "\"", separator = "").length
        } - strings.sumBy { it.length }).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day8::class)
