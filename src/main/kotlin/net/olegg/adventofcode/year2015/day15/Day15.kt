package net.olegg.adventofcode.year2015.day15

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/15">Year 2015, Day 15</a>
 */
class Day15 : DayOf2015(15) {
    val spoons = 100
    val pattern = ".* (-?\\d+)\\b.* (-?\\d+)\\b.* (-?\\d+)\\b.* (-?\\d+)\\b.* (-?\\d+)\\b.*".toPattern()
    val items = data.lines().map {
        val matcher = pattern.matcher(it)
        if (matcher.matches()) {
            (1..matcher.groupCount()).map { matcher.group(it).toInt() }
        } else {
            Array(5) { 0 }.toList()
        }
    }

    override fun first(data: String): Any? {
        val itemsValues = (0..3).map { value -> items.map { it[value] } }
        return splitRange(items.size, spoons)
                .map { split -> itemsValues.map { it.mapIndexed { index, value -> split[index] * value }.sum().coerceAtLeast(0) } }
                .map { it.fold(1) { acc, value -> acc * value } }
                .max().toString()
    }

    override fun second(data: String): Any? {
        val itemsValues = (0..3).map { value -> items.map { it[value] } }
        val calories = items.map { it[4] }
        return splitRange(items.size, spoons)
                .filter { it.mapIndexed { index, value -> calories[index] * value }.sum() == 500 }
                .map { split -> itemsValues.map { it.mapIndexed { index, value -> split[index] * value }.sum().coerceAtLeast(0) } }
                .map { it.fold(1) { acc, value -> acc * value } }
                .max().toString()
    }
}

fun splitRange(splits: Int, sum: Int): List<List<Int>> = if (splits == 1) listOf(listOf(sum)) else {
    (0..sum).flatMap { value -> splitRange(splits - 1, sum - value).map { listOf(value) + it } }
}

fun main(args: Array<String>) = SomeDay.mainify(Day15::class)
