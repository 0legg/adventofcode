package net.olegg.aoc.year2020.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 23](https://adventofcode.com/2020/day/23)
 */
object Day23 : DayOf2020(23) {
  override fun first(): Any? {
    val items = data.map { it.digitToInt() }

    val queue = ArrayDeque(items)

    val min = items.min()
    val max = items.max()

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

  override fun second(): Any? {
    val initialItems = data.map { it.digitToInt() }

    val items = initialItems + ((initialItems.max() + 1)..1_000_000).toList()

    val min = items.min()
    val max = items.max()

    val all = List(1_000_000 + 1) { Item(it) }

    val queue = items.map { all[it] }
    queue.zipWithNext().forEach { (prev, next) ->
      prev.next = next.value
    }
    queue.last().next = queue.first().value

    var head = queue.first()

    repeat(10_000_000) { _ ->
      val took = (0 until 3).scan(head) { acc, _ -> all[acc.next] }
      val excluded = took.map { it.value }
      var place = head.value - 1
      while (place < min || place in excluded) {
        place--
        if (place < min) {
          place = max
        }
      }

      val broken = took[3].next
      val insertion = all[place]
      val next = insertion.next
      insertion.next = took[1].value
      took[3].next = next

      head.next = broken
      head = all[broken]
    }

    val first = all[all[1].next]
    val second = all[first.next]

    return first.value.toLong() * second.value.toLong()
  }

  data class Item(
    val value: Int,
    var next: Int = 0,
  )
}

fun main() = SomeDay.mainify(Day23)
