package net.olegg.adventofcode.year2016.day22

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/22">Year 2016, Day 22</a>
 */
class Day22 : DayOf2016(22) {
    val pattern = "/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%".toRegex()

    override fun first(data: String): Any? {
        val machines = data.lines().filter { it.matches(pattern) }
                .mapNotNull { pattern.find(it)?.groupValues?.let { it.subList(1, 6).map { it.toInt() } } }

        val pairs = machines.filter { it[3] != 0 }.flatMap { a ->
            machines.filter { it != a }
                    .filter { it[4] >= a[3] }
                    .map { b -> a to b }
        }

        return pairs.size.toString()
    }

    override fun second(data: String): Any? {
        val machines = data.lines().filter { it.matches(pattern) }
                .mapNotNull { pattern.find(it)?.groupValues?.let { it.subList(1, 6).map { it.toInt() } } }

        return machines.groupBy { it[1] }.toSortedMap().map { (_, row) ->
            row.sortedBy { it[0] }.map { when (it[3]) {
                0 -> '_'
                in 1..100 -> '.'
                else -> '#'
            } }.joinToString(separator = "")
        }.joinToString(separator = "\n")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day22::class)
