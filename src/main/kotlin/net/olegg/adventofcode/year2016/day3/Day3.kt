package net.olegg.adventofcode.year2016.day3

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/3">Year 2016, Day 3</a>
 */
class Day3 : DayOf2016(3) {
    override fun first(): String {
        return data.split("\n").map {
            it.trim().split("\\s+".toRegex()).map { it.toInt() }.sorted()
        }.count { it[0] + it[1] > it[2] }.toString()
    }

    override fun second(): String {
        val rows = data.split("\n").map {
            it.trim().split("\\s+".toRegex()).map { it.toInt() }
        }

        val columns = (1..rows.size / 3)
                .map { it * 3 }
                .map { rows.take(it).takeLast(3) }
                .map { listOf(
                        listOf(it[0][0], it[1][0], it[2][0]),
                        listOf(it[0][1], it[1][1], it[2][1]),
                        listOf(it[0][2], it[1][2], it[2][2])
                ) }
                .flatten()
                .map { it.sorted() }

        return columns.count { it[0] + it[1] > it[2] }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day3::class)
