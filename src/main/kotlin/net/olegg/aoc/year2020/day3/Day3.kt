package net.olegg.aoc.year2020.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 3](https://adventofcode.com/2020/day/3)
 */
object Day3 : DayOf2020(3) {
  override fun first(data: String): Any? {
    val forest = data
        .trim()
        .lines()

    return forest.mapIndexed { row, s -> s[(row * 3) % s.length] }.count { it == '#' }
  }

  override fun second(data: String): Any? {
    val forest = data
        .trim()
        .lines()

    val slopes = listOf(
        Vector2D(1, 1),
        Vector2D(3, 1),
        Vector2D(5, 1),
        Vector2D(7, 1),
        Vector2D(1, 2),
    )

    val paths = slopes.map { step ->
      generateSequence(Vector2D()) { it + step }.takeWhile { it.y < forest.size }
    }

    val values = paths.map { sequence ->
      sequence.map { forest[it.y][it.x % forest[it.y].length] }.count { it == '#' }.toLong()
    }

    return values.reduce { a, b -> a * b }
  }
}

fun main() = SomeDay.mainify(Day3)
