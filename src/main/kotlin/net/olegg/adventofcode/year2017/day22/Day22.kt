package net.olegg.adventofcode.year2017.day22

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/22">Year 2017, Day 22</a>
 */
class Day22 : DayOf2017(22) {
  override fun first(data: String): Any? {
    val vectors = listOf(
        -1 to 0,
        0 to 1,
        1 to 0,
        0 to -1
    )

    val map = mutableMapOf<Pair<Int, Int>, Boolean>().apply {
      val raw = data.trim().lines().map { it.map { it == '#' } }
      raw.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
          put(y - raw.size / 2 to x - raw.size / 2, value)
        }
      }
    }

    return (0 until 10000).fold(Triple((0 to 0), 0, 0)) { acc, _ ->
      val curr = map.getOrDefault(acc.first, false)
      val dir = (acc.second + (if (curr) 1 else vectors.size - 1)) % vectors.size
      map.put(acc.first, !curr)
      return@fold Triple(
          (acc.first.first + vectors[dir].first to acc.first.second + vectors[dir].second),
          dir,
          if (curr) acc.third else acc.third + 1)
    }.third
  }

  override fun second(data: String): Any? {
    val vectors = listOf(
        -1 to 0,
        0 to 1,
        1 to 0,
        0 to -1
    )

    val map = mutableMapOf<Pair<Int, Int>, Int>().apply {
      val raw = data.trim().lines().map { it.map { ".W#F".indexOf(it) } }
      raw.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
          put(y - raw.size / 2 to x - raw.size / 2, value)
        }
      }
    }

    return (0 until 10000000).fold(Triple((0 to 0), 0, 0)) { acc, _ ->
      val curr = map.getOrDefault(acc.first, 0)
      val dir = when (curr) {
        0 -> acc.second + (vectors.size - 1)
        1 -> acc.second
        2 -> acc.second + 1
        3 -> acc.second + (vectors.size / 2)
        else -> acc.second
      } % vectors.size
      map.put(acc.first, (curr + 1) % 4)
      return@fold Triple(
          (acc.first.first + vectors[dir].first to acc.first.second + vectors[dir].second),
          dir,
          if (curr == 1) acc.third + 1 else acc.third)
    }.third
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day22::class)
