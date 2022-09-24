package net.olegg.aoc.year2021.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * See [Year 2021, Day 5](https://adventofcode.com/2021/day/5)
 */
object Day5 : DayOf2021(5) {
  override fun first(): Any? {
    val vectors = data.trim()
      .lines()
      .map { it.split(" -> ") }
      .map { line ->
        line.first().parseInts(",").let { Vector2D(it.first(), it.last()) } to
          line.last().parseInts(",").let { Vector2D(it.first(), it.last()) } }

    val points = mutableMapOf<Vector2D, Int>()

    vectors
      .filterNot { (begin, end) -> begin == end }
      .filter { (begin, end) -> begin.x == end.x || begin.y == end.y }
      .forEach { (begin, end) ->
        val dir = Vector2D((end.x - begin.x).sign, (end.y - begin.y).sign)
        var curr = begin
        while (curr != end) {
          points[curr] = points.getOrDefault(curr, 0) + 1
          curr = curr + dir
        }
        points[curr] = points.getOrDefault(curr, 0) + 1
      }

    return points.count { it.value > 1 }
  }

  override fun second(): Any? {
    val vectors = data.trim()
      .lines()
      .map { it.split(" -> ") }
      .map { line ->
        line.first().parseInts(",").let { Vector2D(it.first(), it.last()) } to
          line.last().parseInts(",").let { Vector2D(it.first(), it.last()) } }

    val points = mutableMapOf<Vector2D, Int>()

    vectors
      .filterNot { (begin, end) -> begin == end }
      .filter { (begin, end) ->
        begin.x == end.x ||
          begin.y == end.y ||
          (end.x - begin.x).absoluteValue == (end.y - begin.y).absoluteValue
      }
      .forEach { (begin, end) ->
        val dir = Vector2D((end.x - begin.x).sign, (end.y - begin.y).sign)
        var curr = begin
        while (curr != end) {
          points[curr] = points.getOrDefault(curr, 0) + 1
          curr = curr + dir
        }
        points[curr] = points.getOrDefault(curr, 0) + 1
      }

    return points.count { it.value > 1 }
  }
}

fun main() = SomeDay.mainify(Day5)
