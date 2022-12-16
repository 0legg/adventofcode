package net.olegg.aoc.year2022.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 16](https://adventofcode.com/2022/day/16)
 */
object Day16 : DayOf2022(16) {
  private val pattern = "Valve (.*) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex()
  override fun first(): Any? {
    val valves = lines
      .mapNotNull { pattern.find(it)?.destructured?.toList() }
      .associateBy(
        keySelector = { it.first() },
        valueTransform = { tokens -> tokens[1].toInt() to tokens[2].split(", ") }
      )

    val start = State(
      position = "AA",
      pressure = 0,
      timeLeft = 30,
      open = emptySet(),
    )
    val visited = mutableMapOf<Pair<String, Set<String>>, MutableList<Pair<Int, Int>>>()
    val queue = ArrayDeque(listOf(start))
    var best = 0
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val visit = visited.getOrPut(curr.position to curr.open) { mutableListOf() }
      if (visit.any { it.first >= curr.timeLeft && it.second >= curr.pressure }) {
        continue
      } else {
        visit += curr.timeLeft to curr.pressure
      }
      val valve = valves.getValue(curr.position)
      best = maxOf(best, curr.pressure)
      if (curr.timeLeft > 0) {
        if (curr.position !in curr.open && valve.first > 0) {
          queue.addLast(
            State(
              position = curr.position,
              pressure = curr.pressure + valve.first * (curr.timeLeft - 1),
              timeLeft = curr.timeLeft - 1,
              open = curr.open + curr.position,
            )
          )
        }
        valve.second.forEach { next ->
          queue.addLast(
            State(
              position = next,
              pressure = curr.pressure,
              timeLeft = curr.timeLeft - 1,
              open = curr.open,
            )
          )
        }
      }
    }

    return best
  }

  data class State(
    val position: String,
    val pressure: Int,
    val timeLeft: Int,
    val open: Set<String>,
  )
}

fun main() = SomeDay.mainify(Day16)
