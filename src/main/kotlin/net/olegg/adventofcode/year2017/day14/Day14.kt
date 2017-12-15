package net.olegg.adventofcode.year2017.day14

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import java.util.ArrayDeque

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

    override fun second(data: String): String {
        val key = data.trimIndent()

        val vector = listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
        )

        val result = (0..127).map { "$key-$it" }
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
                            .map { Integer.toBinaryString(it).padStart(8, '0').toList() }
                            .flatten()
                            .toMutableList()
                }
                .toMutableList()

        var regions = 0
        while (result.any { it.any { it == '1' } }) {
            regions += 1
            val row = result.indexOfFirst { it.any { it == '1' } }
            val position = row to result[row].indexOfFirst { it == '1' }

            val toVisit = ArrayDeque(listOf(position))
            while (toVisit.isNotEmpty()) {
                val curr = toVisit.pop()
                result[curr.first][curr.second] = '0'
                vector
                        .map { it.first + curr.first to it.second + curr.second }
                        .filter { it.first in result.indices }
                        .filter { it.second in result[it.first].indices }
                        .filter { result[it.first][it.second] == '1' }
                        .forEach {
                            result[it.first][it.second] = '0'
                            toVisit.push(it)
                        }
            }
        }

        return regions.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day14::class)
