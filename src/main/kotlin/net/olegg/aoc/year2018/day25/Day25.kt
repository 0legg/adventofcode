package net.olegg.aoc.year2018.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.UnionFind
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2018.DayOf2018
import kotlin.math.abs

/**
 * See [Year 2018, Day 25](https://adventofcode.com/2018/day/25)
 */
object Day25 : DayOf2018(25) {
  override fun first(data: String): Any? {
    val points = data.trim()
      .lines()
      .map { it.parseInts(",") }

    val uf = UnionFind(points.size)

    points.forEachIndexed { indexA, pointA ->
      points.forEachIndexed { indexB, pointB ->
        if (indexA != indexB) {
          if (pointA.zip(pointB).map { abs(it.first - it.second) }.sum() <= 3) {
            uf.union(indexA, indexB)
          }
        }
      }
    }

    return uf.count
  }
}

fun main() = SomeDay.mainify(Day25)
