package net.olegg.aoc.year2020.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 22](https://adventofcode.com/2020/day/22)
 */
object Day22 : DayOf2020(22) {
  override fun first(data: String): Any? {
    val (p1, p2) = data
      .trim()
      .split("\n\n")
      .map { it.lines().drop(1) }
      .map { ArrayDeque(it.map { card -> card.toInt() }) }
      .toPair()

    while (p1.isNotEmpty() && p2.isNotEmpty()) {
      val v1 = p1.removeFirst()
      val v2 = p2.removeFirst()

      if (v1 > v2) {
        p1 += v1
        p1 += v2
      } else {
        p2 += v2
        p2 += v1
      }
    }

    val result = p1 + p2

    return result.mapIndexed { index, value -> (result.size - index) * value }.sum()
  }

  override fun second(data: String): Any? {
    val (p1, p2) = data
      .trim()
      .split("\n\n")
      .map { it.lines().drop(1) }
      .map { ArrayDeque(it.map { card -> card.toInt() }) }
      .toPair()

    while (p1.isNotEmpty() && p2.isNotEmpty()) {
      val v1 = p1.removeFirst()
      val v2 = p2.removeFirst()

      val p1win = if (v1 <= p1.size && v2 <= p2.size) {
        val l1 = p1.take(v1)
        val l2 = p2.take(v2)
        playRecursive(l1, l2)
      } else {
        v1 > v2
      }

      if (p1win) {
        p1 += v1
        p1 += v2
      } else {
        p2 += v2
        p2 += v1
      }
    }

    val result = p1 + p2

    return result.mapIndexed { index, value -> (result.size - index) * value }.sum()
  }

  private fun playRecursive(in1: List<Int>, in2: List<Int>): Boolean {
    val cache = mutableSetOf<Pair<List<Int>, List<Int>>>()
    val p1 = ArrayDeque(in1)
    val p2 = ArrayDeque(in2)

    while (p1.isNotEmpty() && p2.isNotEmpty()) {
      val l1 = p1.toList()
      val l2 = p2.toList()
      if (l1 to l2 in cache) {
        return true
      }
      cache.add(l1 to l2)

      val v1 = p1.removeFirst()
      val v2 = p2.removeFirst()

      val p1win = if (v1 <= p1.size && v2 <= p2.size) {
        val q1 = p1.take(v1)
        val q2 = p2.take(v2)
        playRecursive(q1, q2)
      } else {
        v1 > v2
      }

      if (p1win) {
        p1 += v1
        p1 += v2
      } else {
        p2 += v2
        p2 += v1
      }
    }

    return p1.isNotEmpty()
  }
}

fun main() = SomeDay.mainify(Day22)
