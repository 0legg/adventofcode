package net.olegg.adventofcode.year2017.day14

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/14">Year 2017, Day 14</a>
 */
class Day14 : DayOf2017(14) {
    override fun first(data: String): String {
        val key = data.trimIndent()
        return (0..127).map { "$key-$it" }
                .map {
                    it.map { it.toInt() }
                            .let { it + listOf(17, 31, 73, 47, 23) }
                            .let { list -> (0 until 64).fold(emptyList<Int>()) { acc, _ -> acc + list } }
                            .foldIndexed(List(256) { it } to 0) { index, acc, value ->
                                val prev = acc.first + acc.first
                                val curr = prev.subList(0, acc.second) + prev.subList(acc.second, acc.second + value).reversed() + prev.subList(acc.second + value, prev.size)
                                val next = (curr.subList(acc.first.size, acc.first.size + acc.second) + curr.subList(acc.second, acc.first.size))
                                return@foldIndexed next to ((acc.second + value + index) % acc.first.size)
                            }
                            .first
                            .chunked(16) {
                                it.fold(0) { acc, value -> acc xor value }
                            }
                            .map { Integer.bitCount(it) }
                            .sum()
                }
                .sum()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day14::class)
