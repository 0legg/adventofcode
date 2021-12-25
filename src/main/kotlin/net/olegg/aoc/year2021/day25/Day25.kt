package net.olegg.aoc.year2021.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 25](https://adventofcode.com/2021/day/25)
 */
object Day25 : DayOf2021(25) {
  override fun first(data: String): Any? {
    val map = data.trim()
      .lines()
      .map { it.toList() }

    val height = map.size
    val width = map.first().size

    val startEast = map.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, char ->
        Vector2D(x, y).takeIf { char == '>' }
      }
    }.toSet()

    val startSouth = map.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, char ->
        Vector2D(x, y).takeIf { char == 'v' }
      }
    }.toSet()

    return generateSequence(1) { it + 1 }
      .scan(Triple(0, startEast, startSouth)) { (_, east, south), step ->
        val newEast = east.map { curr ->
          val new = Vector2D((curr.x + 1) % width, curr.y)
          if (new !in east && new !in south) new else curr
        }.toSet()

        val newSouth = south.map { curr ->
          val new = Vector2D(curr.x, (curr.y + 1) % height)
          if (new !in newEast && new !in south) new else curr
        }.toSet()

        Triple(step, newEast, newSouth)
      }
      .windowed(2)
      .first { it.first().second == it.last().second && it.first().third == it.last().third }
      .last()
      .first
  }
}

fun main() = SomeDay.mainify(Day25)
