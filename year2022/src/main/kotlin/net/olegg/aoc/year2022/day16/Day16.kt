package net.olegg.aoc.year2022.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022
import java.util.BitSet
import java.util.PriorityQueue

/**
 * See [Year 2022, Day 16](https://adventofcode.com/2022/day/16)
 */
object Day16 : DayOf2022(16) {
  private val pattern = "Valve (.*) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex()
  override fun first(): Any? {
    val valvesRaw = lines
      .mapNotNull { pattern.find(it)?.destructured?.toList() }
      .associateBy(
        keySelector = { it.first() },
        valueTransform = { tokens -> tokens[1].toInt() to tokens[2].split(", ") }
      )
    val size = valvesRaw.size
    val valvesMapping = valvesRaw
      .keys
      .mapIndexed { index, value -> value to index }
      .toMap()
    val valves = valvesRaw
      .mapKeys { valvesMapping.getValue(it.key) }
      .mapValues { (_, value) ->
        value.first to value.second.map { valvesMapping.getValue(it) }
      }

    val start = State(
      position = valvesMapping.getValue("AA"),
      pressure = 0,
      timeLeft = 30,
      open = BitSet(size),
    )
    val visited = mutableMapOf<Pair<Int, BitSet>, MutableMap<Int, Int>>()
    val queue = ArrayDeque(listOf(start))
    var best = 0
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val visit = visited.getOrPut(curr.position to curr.open) { mutableMapOf() }
      if (visit.any { it.key >= curr.timeLeft && it.value >= curr.pressure }) {
        continue
      } else {
        visit[curr.timeLeft] = curr.pressure
      }
      val valve = valves.getValue(curr.position)
      best = maxOf(best, curr.pressure)
      if (curr.timeLeft > 0) {
        if (!curr.open.get(curr.position) && valve.first > 0) {
          queue.addLast(
            State(
              position = curr.position,
              pressure = curr.pressure + valve.first * (curr.timeLeft - 1),
              timeLeft = curr.timeLeft - 1,
              open = BitSet(size).apply {
                or(curr.open)
                set(curr.position)
              },
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

  override fun second(): Any? {
    val valvesRaw = lines
      .mapNotNull { pattern.find(it)?.destructured?.toList() }
      .associateBy(
        keySelector = { it.first() },
        valueTransform = { tokens -> tokens[1].toInt() to tokens[2].split(", ") }
      )
    val size = valvesRaw.size
    val valvesMapping = valvesRaw
      .keys
      .mapIndexed { index, value -> value to index }
      .toMap()
    val valves = valvesRaw
      .mapKeys { valvesMapping.getValue(it.key) }
      .mapValues { (_, value) ->
        value.first to value.second.map { valvesMapping.getValue(it) }
      }

    val top = valves
      .map { it.value.first }
      .sortedDescending()
      .windowed(2)
      .map { it.sum() }
      .runningReduce { acc, value -> acc + value }
      .flatMap { listOf(it, it) }
      .let { listOf(0) + it }
      .mapIndexed { index, value -> index * value }

    val aa = valvesMapping.getValue("AA")
    val start = State2(
      positions = aa to aa,
      pressure = 0,
      timeLeft = 26,
      open = BitSet(size),
    )
    val visited = mutableMapOf<Pair<Int, BitSet>, MutableMap<Int, MutableMap<Int, Int>>>()
    val queue = PriorityQueue<State2>(compareByDescending { it.pressure })
    queue.add(start)
    var best = 0
    while (queue.isNotEmpty()) {
      val curr = queue.remove()
      val visit = visited
        .getOrPut(curr.positions.first to curr.open) { mutableMapOf() }
        .getOrPut(curr.positions.second) { mutableMapOf() }
      if (visit.any { it.key >= curr.timeLeft && it.value >= curr.pressure }) {
        continue
      } else {
        visit[curr.timeLeft] = curr.pressure
      }
      if (curr.pressure + top.getOrElse(curr.timeLeft - 1) { top.last() } < best) {
        continue
      }
      best = maxOf(best, curr.pressure)
      if (curr.timeLeft > 0) {
        val valve1 = valves.getValue(curr.positions.first)
        val valve2 = valves.getValue(curr.positions.second)

        val firstOpen = !curr.open.get(curr.positions.first) && valve1.first > 0
        val secondOpen = !curr.open.get(curr.positions.second) && valve2.first > 0

        if (firstOpen && secondOpen && curr.positions.first != curr.positions.second) {
          val newState = State2(
            positions = curr.positions,
            pressure = curr.pressure + (valve1.first + valve2.first) * (curr.timeLeft - 1),
            timeLeft = curr.timeLeft - 1,
            open = BitSet(size).apply {
              or(curr.open)
              set(curr.positions.first)
              set(curr.positions.second)
            },
          )
          val newVisit = visited
            .getOrElse(newState.positions.first to newState.open) { emptyMap() }
            .getOrElse(newState.positions.second) { emptyMap() }
          if (newVisit.none { it.key >= newState.timeLeft && it.value >= newState.pressure }) {
            if (newState.pressure + top.getOrElse(newState.timeLeft - 1) { top.last() } > best) {
              queue.add(newState)
            }
          }
        }

        if (firstOpen) {
          valve2.second.forEach { next ->
            val newState = State2(
              positions = minOf(curr.positions.first, next) to maxOf(curr.positions.first, next),
              pressure = curr.pressure + valve1.first * (curr.timeLeft - 1),
              timeLeft = curr.timeLeft - 1,
              open = BitSet(size).apply {
                or(curr.open)
                set(curr.positions.first)
              },
            )

            val newVisit = visited
              .getOrElse(newState.positions.first to newState.open) { emptyMap() }
              .getOrElse(newState.positions.second) { emptyMap() }
            if (newVisit.none { it.key >= newState.timeLeft && it.value >= newState.pressure }) {
              if (newState.pressure + top.getOrElse(newState.timeLeft - 1) { top.last() } > best) {
                queue.add(newState)
              }
            }
          }
        }

        if (secondOpen && curr.positions.first != curr.positions.second) {
          valve1.second.forEach { next ->
            val newState = State2(
              positions = minOf(curr.positions.second, next) to maxOf(curr.positions.second, next),
              pressure = curr.pressure + valve2.first * (curr.timeLeft - 1),
              timeLeft = curr.timeLeft - 1,
              open = BitSet(size).apply {
                or(curr.open)
                set(curr.positions.second)
              }
            )

            val newVisit = visited
              .getOrElse(newState.positions.first to newState.open) { emptyMap() }
              .getOrElse(newState.positions.second) { emptyMap() }
            if (newVisit.none { it.key >= newState.timeLeft && it.value >= newState.pressure }) {
              if (newState.pressure + top.getOrElse(newState.timeLeft - 1) { top.last() } > best) {
                queue.add(newState)
              }
            }
          }
        }

        valve1.second.forEach { firstNext ->
          valve2.second.forEach { secondNext ->
            val newState = State2(
              positions = minOf(firstNext, secondNext) to maxOf(firstNext, secondNext),
              pressure = curr.pressure,
              timeLeft = curr.timeLeft - 1,
              open = curr.open,
            )

            val newVisit = visited
              .getOrElse(newState.positions.first to newState.open) { emptyMap() }
              .getOrElse(newState.positions.second) { emptyMap() }
            if (newVisit.none { it.key >= newState.timeLeft && it.value >= newState.pressure }) {
              if (newState.pressure + top.getOrElse(newState.timeLeft - 1) { top.last() } > best) {
                queue.add(newState)
              }
            }
          }
        }
      }
    }

    return best
  }

  data class State(
    val position: Int,
    val pressure: Int,
    val timeLeft: Int,
    val open: BitSet,
  )

  data class State2(
    val positions: Pair<Int, Int>,
    val pressure: Int,
    val timeLeft: Int,
    val open: BitSet,
  )
}

fun main() = SomeDay.mainify(Day16)
