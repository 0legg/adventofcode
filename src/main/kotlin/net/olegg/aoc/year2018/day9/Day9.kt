package net.olegg.aoc.year2018.day9

import java.util.LinkedList
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 9](https://adventofcode.com/2018/day/9)
 */
object Day9 : DayOf2018(9) {
  private val PATTERN = "(\\d++) players; last marble is worth (\\d++) points".toRegex()

  override fun first(data: String): Any? {
    val (players, points) = PATTERN.matchEntire(data.trim())?.destructured?.toList()?.map { it.toInt() }
        ?: throw IllegalArgumentException()

    return play(players, points).maxOrNull()
  }

  override fun second(data: String): Any? {
    val (players, points) = PATTERN.matchEntire(data.trim())?.destructured?.toList()?.map { it.toInt() }
        ?: throw IllegalArgumentException()

    return play(players, points * 100).maxOrNull()
  }

  private fun play(players: Int, points: Int): List<Long> {
    val result = LongArray(players)
    val loop = LinkedList(listOf(0))
    var iterator = loop.listIterator()

    (1..points).forEach { marble ->
      if (marble % 23 == 0) {
        val player = (marble - 1) % players
        result[player] += marble.toLong()
        repeat(7) {
          if (!iterator.hasPrevious()) {
            iterator = loop.listIterator(loop.size)
          }
          iterator.previous()
        }
        result[player] += iterator.previous().toLong()
        iterator.remove()
        if (!iterator.hasNext()) {
          iterator = loop.listIterator()
        }
        iterator.next()
      } else {
        if (!iterator.hasNext()) {
          iterator = loop.listIterator()
        }
        iterator.next()
        iterator.add(marble)
      }
    }

    return result.toList()
  }
}

fun main() = SomeDay.mainify(Day9)
