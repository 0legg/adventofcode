package net.olegg.adventofcode.year2017.day7

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/7">Year 2017, Day 7</a>
 */
class Day7 : DayOf2017(7) {
    override fun first(data: String): String {
        return data.lines()
                .map { it.split("\\s".toRegex()).map { it.replace(",", "") } }
                .fold(emptySet<String>() to emptySet<String>()) { acc, list ->
                    val used = if (list.size > 3) acc.second + list.subList(3, list.size) else acc.second
                    val free = (if (list[0] !in acc.second) acc.first + list[0] else acc.first) - acc.second
                    return@fold free to used
                }
                .first
                .first()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day7::class)
