package net.olegg.aoc.year2018.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 3](https://adventofcode.com/2018/day/3)
 */
object Day3 : DayOf2018(3) {
  override fun first(): Any? {
    val requests = lines.mapNotNull { Request.fromString(it) }
    val width = requests.maxOf { it.left + it.width }
    val height = requests.maxOf { it.top + it.height }

    val field = Array(height) { Array(width) { 0 } }

    requests.forEach { request ->
      (request.top..<request.top + request.height).forEach { y ->
        (request.left..<request.left + request.width).forEach { x ->
          field[y][x] += 1
        }
      }
    }

    return field.sumOf { row -> row.count { it > 1 } }
  }

  override fun second(): Any? {
    val requests = lines.mapNotNull { Request.fromString(it) }

    return requests
      .first { request -> requests.all { it.notIntersects(request) } }
      .id
  }

  data class Request(
    val id: Int,
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int
  ) {
    companion object {
      private val PATTERN = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)".toRegex()

      fun fromString(data: String): Request? {
        return PATTERN.matchEntire(data)?.let { match ->
          val tokens = match.destructured.toList().map { it.toInt() }
          Request(
            id = tokens[0],
            left = tokens[1],
            top = tokens[2],
            width = tokens[3],
            height = tokens[4],
          )
        }
      }
    }

    fun notIntersects(other: Request): Boolean {
      return (id == other.id) ||
        (left + width <= other.left) ||
        (other.left + other.width <= left) ||
        (top + height <= other.top) ||
        (other.top + other.height <= top)
    }
  }
}

fun main() = SomeDay.mainify(Day3)
