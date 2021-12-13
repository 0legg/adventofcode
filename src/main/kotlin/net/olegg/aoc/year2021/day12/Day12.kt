package net.olegg.aoc.year2021.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 12](https://adventofcode.com/2021/day/12)
 */
object Day12 : DayOf2021(12) {
  override fun first(data: String): Any? {
    val edges = data.trim()
      .lines()
      .map { it.split("-") }
      .flatMap { listOf(it.first() to it.last(), it.last() to it.first()) }
      .groupBy(
        keySelector = { it.first },
        valueTransform = { it.second },
      )
      .mapValues { it.value.sorted() }

    val visited = mutableSetOf<List<String>>()
    val finish = mutableSetOf<List<String>>()
    val queue = ArrayDeque(listOf(listOf("start")))

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (curr !in visited) {
        visited += curr
        val tail = curr.last()
        if (tail == "end") {
          finish += curr
        } else {
          queue += edges[tail].orEmpty()
            .filter { it.uppercase() == it || it !in curr }
            .map { curr + it }
            .filter { it !in visited }
        }
      }
    }

    return finish.size
  }

  override fun second(data: String): Any? {
    val edges = data.trim()
      .lines()
      .map { it.split("-") }
      .flatMap { listOf(it.first() to it.last(), it.last() to it.first()) }
      .groupBy(
        keySelector = { it.first },
        valueTransform = { it.second },
      )
      .mapValues { it.value.sorted() }

    val visited = mutableSetOf<List<String>>()
    val finish = mutableSetOf<List<String>>()
    val queue = ArrayDeque(listOf(listOf("start") to false))

    while (queue.isNotEmpty()) {
      val (curr, twice) = queue.removeFirst()
      if (curr !in visited) {
        visited += curr
        val tail = curr.last()
        val nexts = edges[tail].orEmpty()
        if ("end" in nexts) {
          finish += curr + "end"
        }
        queue += nexts
          .filter { it !in setOf("start", "end") }
          .filter { it.uppercase() == it || it !in curr }
          .map { curr + it }
          .filter { it !in visited }
          .map { it to twice }

        if (!twice) {
          queue += nexts
            .filter { it !in setOf("start", "end") }
            .filter { it.lowercase() == it && it in curr }
            .map { curr + it }
            .filter { it !in visited }
            .map { it to true }
        }
      }
    }

    return finish.size
  }
}

fun main() = SomeDay.mainify(Day12)
