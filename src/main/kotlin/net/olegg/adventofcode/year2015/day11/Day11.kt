package net.olegg.adventofcode.year2015.day11

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.series
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/11">Year 2015, Day 11</a>
 */
class Day11 : DayOf2015(11) {
    fun passwordList(password: String) = generateSequence(password) {
        it.toList().foldRight(Pair("", true)) { value, acc ->
            Pair(
                    (if (!acc.second) value else when (value) {
                        'z' -> 'a'
                        else -> value + 1
                    }) + acc.first,
                    acc.second && value == 'z'
            )
        }.first
    }

    fun password(password: String): String {
        return passwordList(password).drop(1)
                .filterNot { string -> "iol".any { string.contains(it) } }
                .filter { string -> ('a'..'x').map { String(charArrayOf(it, it + 1, it + 2)) }.any { string.contains(it) } }
                .filter { it.toList().series().filter { it.size > 1 }.flatMap { it }.joinToString(separator = "").length > 3 }
                .first()
    }

    override fun first(data: String): String {
        return password(data)
    }

    override fun second(data: String): String {
        return password(password(data))
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day11::class)
