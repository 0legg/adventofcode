package net.olegg.aoc.year2017.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 14](https://adventofcode.com/2017/day/14)
 */
object Day14 : DayOf2017(14) {
  override fun first(data: String): Any? {
    val key = data.trimIndent()
    return (0..127)
      .map { "$key-$it" }
      .sumOf { line ->
        line
          .map { it.code }
          .let { it + listOf(17, 31, 73, 47, 23) }
          .let { list ->
            (0 until 64).fold(emptyList<Int>()) { acc, _ -> acc + list }
          }
          .foldIndexed(List(256) { it } to 0) { index, acc, value ->
            val prev = acc.first + acc.first
            val curr = prev.subList(0, acc.second) +
              prev.subList(acc.second, acc.second + value).reversed() +
              prev.subList(acc.second + value, prev.size)
            val next = curr.subList(acc.first.size, acc.first.size + acc.second) +
              curr.subList(acc.second, acc.first.size)
            return@foldIndexed next to ((acc.second + value + index) % acc.first.size)
          }
          .first
          .chunked(16) { chunk -> chunk.reduce { acc, value -> acc xor value } }
          .sumOf { Integer.bitCount(it) }
      }
  }

  override fun second(data: String): Any? {
    val key = data.trimIndent()

    val result = (0..127)
      .map { "$key-$it" }
      .map { line ->
        line
          .map { it.code }
          .let { it + listOf(17, 31, 73, 47, 23) }
          .let { list -> (0 until 64).fold(emptyList<Int>()) { acc, _ -> acc + list } }
          .foldIndexed(List(256) { it } to 0) { index, acc, value ->
            val prev = acc.first + acc.first
            val curr = prev.subList(0, acc.second) +
              prev.subList(acc.second, acc.second + value).reversed() +
              prev.subList(acc.second + value, prev.size)
            val next = curr.subList(acc.first.size, acc.first.size + acc.second) +
              curr.subList(acc.second, acc.first.size)
            return@foldIndexed next to ((acc.second + value + index) % acc.first.size)
          }
          .first
          .chunked(16) { chunk -> chunk.reduce { acc, value -> acc xor value } }
          .flatMap { Integer.toBinaryString(it).padStart(8, '0').toList() }
          .toMutableList()
      }
      .toMutableList()

    var regions = 0
    while (result.any { '1' in it }) {
      regions++
      val row = result.indexOfFirst { '1' in it }
      val position = Vector2D(result[row].indexOfFirst { it == '1' }, row)

      val toVisit = ArrayDeque(listOf(position))
      while (toVisit.isNotEmpty()) {
        val curr = toVisit.removeFirst()
        result[curr] = '0'
        Neighbors4
          .map { curr + it.step }
          .filter { result[it] == '1' }
          .forEach { point ->
            result[point.y][point.x] = '0'
            toVisit += point
          }
      }
    }

    return regions
  }
}

fun main() = SomeDay.mainify(Day14)
