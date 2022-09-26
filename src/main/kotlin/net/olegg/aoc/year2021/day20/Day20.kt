package net.olegg.aoc.year2021.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 20](https://adventofcode.com/2021/day/20)
 */
object Day20 : DayOf2021(20) {
  override fun first(): Any? {
    return solve(2)
  }

  override fun second(): Any? {
    return solve(50)
  }

  private fun solve(steps: Int): Int {
    val (rawAlgo, rawSource) = data.trim().split("\n\n")
    val algo = rawAlgo.toList()
    val source = rawSource.trim()
      .lines()
      .map { it.toList() }

    return (0 until steps)
      .fold(source to '.') { acc, _ ->
        enhance(acc.first, algo, acc.second)
      }
      .first
      .sumOf { line -> line.count { it == '#' } }
  }

  private fun enhance(
    source: List<List<Char>>,
    algo: List<Char>,
    blank: Char,
  ): Pair<List<List<Char>>, Char> {
    val height = source.size
    val width = source.first().size
    return (-1..height).map { y ->
      (-1..width).map { x ->
        val chars = (-1..1).flatMap { dy ->
          (-1..1).map { dx ->
            source[Vector2D(x + dx, y + dy)] ?: blank
          }
        }.map { if (it == '#') 1 else 0 }
        algo[chars.joinToString("").toInt(2)]
      }
    } to algo[if (blank == '#') 511 else 0]
  }
}

fun main() = SomeDay.mainify(Day20)
