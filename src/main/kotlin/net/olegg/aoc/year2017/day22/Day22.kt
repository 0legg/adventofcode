package net.olegg.aoc.year2017.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.Companion.CCW
import net.olegg.aoc.utils.Directions.Companion.CW
import net.olegg.aoc.utils.Directions.Companion.REV
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 22](https://adventofcode.com/2017/day/22)
 */
object Day22 : DayOf2017(22) {
  override fun first(): Any? {
    val map = mutableMapOf<Vector2D, Boolean>().apply {
      val raw = lines.map { line -> line.map { it == '#' } }
      raw.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
          put(Vector2D(x - raw.size / 2, y - raw.size / 2), value)
        }
      }
    }

    return (0 until 10000).fold(Triple(Vector2D(), Directions.U, 0)) { (pos, dir, state), _ ->
      val curr = map.getOrDefault(pos, false)
      val newDir = (if (curr) CW[dir] else CCW[dir]) ?: dir
      map[pos] = !curr
      return@fold Triple(
        pos + newDir.step,
        newDir,
        if (curr) state else state + 1
      )
    }.third
  }

  override fun second(): Any? {
    val map = mutableMapOf<Vector2D, Int>().apply {
      val raw = lines.map { line -> line.map { ".W#F".indexOf(it) } }
      raw.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
          put(Vector2D(x - raw.size / 2, y - raw.size / 2), value)
        }
      }
    }

    return (0 until 10000000).fold(Triple(Vector2D(), Directions.U, 0)) { (pos, dir, state), _ ->
      val curr = map.getOrDefault(pos, 0)
      val newDir = when (curr) {
        0 -> CCW[dir]
        1 -> dir
        2 -> CW[dir]
        3 -> REV[dir]
        else -> dir
      } ?: dir
      map[pos] = (curr + 1) % 4
      return@fold Triple(
        pos + newDir.step,
        newDir,
        if (curr == 1) state + 1 else state
      )
    }.third
  }
}

fun main() = SomeDay.mainify(Day22)
