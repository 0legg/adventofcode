package net.olegg.aoc.year2017.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 16](https://adventofcode.com/2017/day/16)
 */
object Day16 : DayOf2017(16) {
  override fun first(data: String): Any? {
    return data.trimIndent()
      .split(",")
      .fold(StringBuilder("abcdefghijklmnop")) { acc, op ->
        when (op[0]) {
          's' -> {
            val shift = op.substring(1).toInt()
            val toEnd = acc.substring(0, acc.length - shift)
            acc.delete(0, acc.length - shift)
            acc.append(toEnd)
          }
          'x' -> {
            val replace = op.substring(1).split("/").map { it.toInt() }
            val a = acc[replace[0]]
            val b = acc[replace[1]]
            acc[replace[0]] = b
            acc[replace[1]] = a
          }
          'p' -> {
            val replace = op.substring(1).split("/").map { it[0] }
            val a = acc.indexOf(replace[0])
            val b = acc.indexOf(replace[1])
            acc[a] = replace[1]
            acc[b] = replace[0]
          }
        }
        acc
      }
      .toString()
  }

  override fun second(data: String): Any? {
    val dance = data.trimIndent().split(",")

    var curr = "abcdefghijklmnop" to 0
    val seen = mutableMapOf(curr)

    repeat(1000000000) { _ ->
      val next = dance.fold(StringBuilder(curr.first)) { acc, op ->
        when (op[0]) {
          's' -> {
            val shift = op.substring(1).toInt()
            val toEnd = acc.substring(0, acc.length - shift)
            acc.delete(0, acc.length - shift)
            acc.append(toEnd)
          }
          'x' -> {
            val replace = op.substring(1).split("/").map { it.toInt() }
            val a = acc[replace[0]]
            val b = acc[replace[1]]
            acc[replace[0]] = b
            acc[replace[1]] = a
          }
          'p' -> {
            val replace = op.substring(1).split("/").map { it[0] }
            val a = acc.indexOf(replace[0])
            val b = acc.indexOf(replace[1])
            acc[a] = replace[1]
            acc[b] = replace[0]
          }
        }
        acc
      }.toString() to curr.second + 1

      if (next.first in seen.keys) {
        val prev = seen[next.first] ?: 0
        val period = next.second - prev
        return seen.entries
          .first { it.value >= prev && it.value % period == 1_000_000_000 % period }
          .key
      } else {
        seen += next
        curr = next
      }
    }

    return curr.first
  }
}

fun main() = SomeDay.mainify(Day16)
