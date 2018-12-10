package net.olegg.adventofcode.year2016.day24

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.permutations
import net.olegg.adventofcode.year2016.DayOf2016
import java.util.*

/**
 * @see <a href="http://adventofcode.com/2016/day/24">Year 2016, Day 24</a>
 */
class Day24 : DayOf2016(24) {

  private val moves = listOf(
      0 to 1,
      0 to -1,
      1 to 0,
      -1 to 0
  )

  override fun first(data: String): Any? {
    val map = data.lines().map { it.toCharArray().toTypedArray() }

    val locations = ('0'..'7').map { index ->
      map.mapIndexedNotNull { row, chars ->
        if (chars.indexOf(index) != -1) chars.indexOf(index) to row else null
      }
    }.flatten()

    val distances = (0..7).map { dist ->
      val start = locations[dist]
      val visit = mutableMapOf(start to 0)
      val queue = ArrayDeque(listOf(start))

      while (queue.isNotEmpty()) {
        val (x, y) = queue.pop()
        val steps = visit[x to y] ?: 0
        moves
            .map { x + it.first to y + it.second }
            .filterNot { visit.containsKey(it) }
            .filterNot { map[it.second][it.first] == '#' }
            .also { cells ->
              cells.forEach { cell -> visit[cell] = steps + 1 }
              queue.addAll(cells)
            }
      }

      return@map locations.map { visit[it] }
    }

    return (1..7)
        .toList()
        .permutations()
        .map { listOf(0) + it }
        .map { route ->
          route.windowed(2).fold(0) { acc, points ->
            acc + (distances[points[0]][points[1]] ?: 0)
          }
        }
        .min()
  }

  override fun second(data: String): Any? {
    val map = data.lines().map { it.toCharArray().toTypedArray() }

    val locations = ('0'..'7').map { index ->
      map.mapIndexedNotNull { row, chars ->
        if (chars.indexOf(index) != -1) chars.indexOf(index) to row else null
      }
    }.flatten()

    val distances = (0..7).map { dist ->
      val start = locations[dist]
      val visit = mutableMapOf(start to 0)
      val queue = LinkedList(listOf(start))

      while (queue.isNotEmpty()) {
        val (x, y) = queue.pop()
        val steps = visit[x to y] ?: 0
        moves
            .map { x + it.first to y + it.second }
            .filterNot { visit.containsKey(it) }
            .filterNot { map[it.second][it.first] == '#' }
            .also { cells ->
              cells.forEach { cell -> visit[cell] = steps + 1 }
              queue.addAll(cells)
            }
      }

      return@map locations.map { visit[it] }
    }

    return (1..7)
        .toList()
        .permutations()
        .map { listOf(0) + it + listOf(0) }
        .map { route ->
          route.windowed(2).fold(0) { acc, points ->
            acc + (distances[points[0]][points[1]] ?: 0)
          }
        }
        .min()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day24::class)
