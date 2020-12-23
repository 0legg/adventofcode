package net.olegg.aoc.year2020.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 23](https://adventofcode.com/2020/day/23)
 */
object Day23 : DayOf2020(23) {
  override fun first(data: String): Any? {
    val items = data
      .trim()
      .map { it.toString().toInt() }

    val queue = ArrayDeque(items)

    val min = items.minOf { it }
    val max = items.maxOf { it }

    repeat(100) {
      val head = queue.removeFirst()
      val took = listOf(queue.removeFirst(), queue.removeFirst(), queue.removeFirst())

      var next = head - 1
      var position = queue.indexOf(next)
      while (position == -1) {
        next--
        if (next < min) {
          next = max
        }
        position = queue.indexOf(next)
      }
      took.forEachIndexed { index, value ->
        queue.add(position + index + 1, value)
      }
      queue.addLast(head)
    }

    val pos1 = queue.indexOf(1)
    val result = queue.drop(pos1 + 1) + queue.take(pos1)

    return result.joinToString("")
  }
}

fun main() = SomeDay.mainify(Day23)
