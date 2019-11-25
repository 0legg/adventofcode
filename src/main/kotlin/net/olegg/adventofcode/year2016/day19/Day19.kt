package net.olegg.adventofcode.year2016.day19

import java.util.BitSet
import java.util.LinkedList
import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * See [Year 2016, Day 19](https://adventofcode.com/2016/day/19)
 */
class Day19 : DayOf2016(19) {
  override fun first(data: String): Any? {
    val count = data.toInt()
    val elves = BitSet(count)
    elves.set(0, count)
    var position = 0
    repeat(count - 1) {
      val next = maxOf(elves.nextSetBit(position + 1), elves.nextSetBit(0))
      elves.clear(next)
      position = maxOf(elves.nextSetBit(next + 1), elves.nextSetBit(0))
    }

    return (position + 1)
  }

  override fun second(data: String): Any? {
    val count = data.toInt()
    val elves = LinkedList((1..count).toList())

    var target = elves.listIterator(count / 2)
    target.next()
    for (i in count downTo 2) {
      target.remove()
      repeat(i % 2 + 1) {
        if (!target.hasNext()) {
          target = elves.listIterator()
        }
        target.next()
      }
    }

    if (!target.hasNext()) {
      target = elves.listIterator()
    }

    return target.next()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day19::class)
